package fr.ul.miage.ncmnf;

import javax.smartcardio.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SNFCReader {
    public static void main(String[] args) {

        try {
            // Todo : En vous inspirant du code du NFC Reader, récupérez les terminaux

            // TODO : Utiliser le premier terminal disponible
            CardTerminal terminal;

            // Attendre qu'une carte soit insérée
            System.out.println("Veuillez approcher la puce NFC du lecteur...");
            terminal.waitForCardPresent(0);

            // TODO : Connexion de la carte posée sur le terminal
            Card card;
            CardChannel channel = card.getBasicChannel();

            // TODO : Lire les données de la puce NFC avec la méthode fournie
            byte[] data;

            // TODO : Décoder les données NDEF avec la méthode fournie
            String decodedData;
            System.out.println("Données lues de la puce NFC: " + decodedData);

            // Déconnexion de la carte
            card.disconnect(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readNDEFMessage(CardChannel channel) throws CardException {
        int startPage = 4; // Start reading from page 4
        int endPage = 135; // End reading at page 17 (adjust as needed)
        byte[] data = new byte[(endPage - startPage + 1) * 4];


        for (int page = startPage; page <= endPage; page++) {
            byte[] commandAPDU = {(byte) 0xFF, (byte) 0xB0, 0x00, (byte) page, 0x04}; // Command to read 4 bytes from the specified page
            //TODO : Récupérer la réponse ResponseAPDU que le channel en entrée a transmis
            byte[] pageData = response.getData();
            if (pageData.length == 0) {
                return data;
            }

            System.arraycopy(pageData, 0, data, (page - startPage) * 4, pageData.length);
        }

        return data;
    }

    private static String decodeNDEFMessage(byte[] data) {
        // Vérifier si les données commencent par le TLV NDEF (0x03)
        if (data.length < 2 || data[0] != 0x03) {
            return "Données NDEF non valides.";
        }

        // Lire la longueur du message NDEF
        int ndefLength = data[1] & 0xFF;


        // Vérifier si la longueur du NDEF est valide
        if (ndefLength > data.length - 2) {
            return "Longueur NDEF non valide.";
        }

        byte[] ndefHeader = new byte[5];
        System.arraycopy(data, 2, ndefHeader, 0, ndefHeader.length);


        // NDEF Header for URI Record
        if (!(ndefHeader[0] == (byte) 0xD1 // ndefMessage[0] = (byte) 0xD1; // NDEF Header (MB/ME/SR/IL/TNF)
                && ndefHeader[1] == (byte) 0x01 // ndefMessage[1] = (byte) 0x01; // Type Length
                && ndefHeader[3] == (byte) 0x55 // ndefMessage[3] = (byte) 0x55; // Type (URI)
                && ndefHeader[4] == (byte) 0x00)) { // ndefMessage[4] = (byte) 0x00; // URI Identifier Code (0x00 = No prefix)
            return "Le message NDEF est pas de type URI";
        }

        // ndefMessage[2] = (byte) (urlBytes.length + 1); // Payload Length
        int uriLength = (ndefHeader[2] & 0xFF) - 1;

        byte[] uriMessage = new byte[uriLength];
        // Retire le TLV (2 bytes) et le Header (5 bytes)
        System.arraycopy(data, ndefHeader.length + 2, uriMessage, 0, uriLength);

        return new String(uriMessage, StandardCharsets.UTF_8);
    }

}