# Hill Cipher

The folder contains 3 shell script files:

-HillKeys.sh
-HillCipher.sh
-HillDecipher.sh

These scripts compile and run the three programs in the task.

Make sure they are runnable using:

    chmod +x <filename>

## Usage

### HillKeys

HillKeys.sh takes two arguments:
The file to save the encryption matrix 'K' in,
and the file to save the decryption matrix 'D' in. Example:

    ./HillKeys.sh K D


### HillCipher

HillCipher.sh takes three arguments:
The file containing the encryption matrix 'K', the file containing the message 'M',
and the file 'C' to save the cipher text in. Example:

    ./HillCipher.sh K M C

### HillDecipher
HillDecipher.sh takes three arguments:
The file containing the decryption matrix 'D', the file containing the
cipher text 'C', and the file to save the plain text message 'M' to

    ./HillDecipher.sh D C M
