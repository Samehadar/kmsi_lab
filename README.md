# KMSI Labs — Cryptography & Information Security

University coursework on cryptography and information security methods (КМСИ — Криптографические Методы и Средства защиты Информации).

## Structure

### `src/console_ciphers/` — Classical ciphers (console)
- **Caesar cipher** — shift cipher over Cyrillic alphabet
- **Affine cipher** — linear cipher `E(x) = (ax + b) mod m`
- **Vigenère cipher** — polyalphabetic substitution
- **Transposition cipher** — columnar transposition
- **Feistel cipher** — Feistel network rounds on byte pairs
- **Block cipher** — stub
- **Open text model** — frequency / entropy analysis on Russian text

### `src/sample/` — Labs 2, 3, 5, 6 (JavaFX)
- **LFSR Generator** — linear feedback shift register with nonlinear taps
- **XOR stream cipher** — encrypt/decrypt files using LFSR gamma
- **CRC hash** — CRC-style polynomial hashing with collision analysis
- **ElGamal signature** — ElGamal sign/verify scheme (`lab6/`)

### `src/lab1_lcg/` — Lab 1: Linear Congruential Generator (JavaFX)
- GUI for LCG with parameters `a, c, m, x₀`
- Generates sequence until repetition, derives bit array, reports parity/bit statistics

### `src/lab4_rsa/` — Lab 4: RSA (JavaFX)
- RSA key generation from primes `p, q`
- Encrypt/decrypt Russian text using `BigInteger.modPow`

### `src/p2p_crypto_chat/` — CryptoChat: P2P encrypted messaging
- **ElGamal encryption** scheme
- **Vigenère cipher** (without modular reduction variant)
- **Protocol 1.3** implementation with Trent (trusted third party)
- **Key generation** utilities
- **Unit tests** in `test/`

## Tech Stack
- Java 8
- JavaFX + FXML (GUI labs)
- Maven (CryptoChat module)
- JUnit (CryptoChat tests)
