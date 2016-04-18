# Hill Cipher

The folder contains 3 shell script files:

- HillKeys.sh
- HillCipher.sh
- HillDecipher.sh

These scripts compile and run the three programs in the task.

Make sure they are runnable using:

```bash
chmod +x <filename>
```

## Usage

### HillKeys

HillKeys.sh takes two arguments:
The file to save the encryption matrix _'K_' in,
and the file to save the decryption matrix _'D'_ in. Example:

```bash
./HillKeys.sh K D
```

### HillCipher

HillCipher.sh takes three arguments:
The file containing the encryption matrix _'K'_, the file containing the message _'M'_,
and the file _'C'_ to save the cipher text in. Example:

```bash
./HillCipher.sh K M C
```

### HillDecipher
HillDecipher.sh takes three arguments:
The file containing the decryption matrix _'D'_, the file containing the
cipher text _'C'_, and the file to save the plain text message _'M'_ to

```bash
./HillDecipher.sh D C M
```
