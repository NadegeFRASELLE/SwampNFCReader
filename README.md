# SNFC : Swamp Near Field Communication READER🐸
ShrekNFC or Swamp Near Field Communication is an application that reads messages to an NFC tag.
The writer is included for more fun

## Requirements
The application only works on Windows and with the specified NFC reader and NFC tags.
- Java 21
- NFC reader ([ACR122U from ACS](https://www.acs.com.hk/en/products/3/acr122u-usb-nfc-reader/))
- NFC tag (MIFARE Ultralight)

## Use
1. Clone the repository
2. Set up the NFC reader with the guide available in the repository
3. Generate the jar file
   ```bash
   mvn clean package
   ```
4. Plug in the NFC reader and put the NFC tag on it
5. Run the application
   ```bash
   java -jar target/SwampNFCReader-1.0-SNAPSHOT.jar
   ```

## Authors
- CASTRO MOUCHERON Nicole
- FRASELLE Nadège

Université de Lorraine - 2025
