from Crypto.Cipher import DES
from Crypto.Random import get_random_bytes
import base64

def pad(text):
    while len(text) % 8 != 0:
        text += ' '
    return text

def encrypt(plaintext_str, key_str):
    key = key_str.encode()  # Chuyển đổi khóa từ str sang bytes
    plaintext = pad(plaintext_str).encode()  # Pad và chuyển đổi plaintext từ str sang bytes

    des = DES.new(key, DES.MODE_ECB)
    encrypted_text = des.encrypt(plaintext)
    encrypted_text_b64 = base64.b64encode(encrypted_text).decode('utf-8')
    return encrypted_text_b64

def decrypt(encrypted_text_b64, key_str):
    key = key_str.encode()  # Chuyển đổi khóa từ str sang bytes

    encrypted_text = base64.b64decode(encrypted_text_b64)
    des = DES.new(key, DES.MODE_ECB)
    decrypted_text = des.decrypt(encrypted_text).decode('utf-8')
    return decrypted_text.strip()