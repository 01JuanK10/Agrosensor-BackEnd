import json
import random
import requests
from conexion_internet import obtener_mac
from fechas import obtener_fecha_iso

URL_BASE = "http://backend:8080"
URL_REGISTER_USER = URL_BASE + "/auth/register"
URL_REFRESH_TOKEN = URL_BASE + "/auth/refresh-token"
URL_REGISTER_DEVICE = URL_BASE + "/api/devices/esp32"
URL_MEASUREMENTS_SOIL = URL_BASE + "/api/measurements/soil"

ACCESS_TOKEN = None
REFRESH_TOKEN = None


def registrar_usuario_dispositivo():
    global ACCESS_TOKEN, REFRESH_TOKEN
    mac = obtener_mac()
    data = {
        "name": "ESP32",
        "lastname": "Sensor",
        "username": mac,
        "password": mac,
        "role": "DEVICE"
    }

    try:
        response = requests.post(URL_REGISTER_USER, json=data)
        if response.status_code in (200, 201):
            body = response.json()
            ACCESS_TOKEN = body.get("access_token")
            REFRESH_TOKEN = body.get("refresh_token")
            print("✅ Usuario registrado. Access token obtenido.")
            return True
        else:
            print("Error al registrar usuario:", response.status_code, response.text)
            return False
    except Exception as e:
        print("Error registrando usuario:", e)
        return False


def refrescar_token():
    global ACCESS_TOKEN, REFRESH_TOKEN
    try:
        headers = {
            "Authorization": "Bearer " + REFRESH_TOKEN,
            "Content-Type": "application/json"
        }
        response = requests.post(URL_REFRESH_TOKEN, headers=headers)

        if response.status_code == 200:
            tokens = response.json()
            ACCESS_TOKEN = tokens.get("access_token")
            REFRESH_TOKEN = tokens.get("refresh_token")
            print("🔄 Tokens actualizados correctamente.")
            return True
        else:
            print("Error al refrescar token:", response.status_code)
            return False
    except Exception as e:
        print("Error refrescando token:", e)
        return False


def registrar_dispositivo():
    mac = obtener_mac()
    data = {
        "type": "esp32",
        "id": mac,
        "location": {
            "latitude": 6.25184,
            "longitude": -75.56359,
            "address": "Medellín, Colombia"
        },
        "client": {"cc": 1234567890}
    }

    try:
        response = requests.post(
            URL_REGISTER_DEVICE,
            json=data,
            headers={
                "Content-Type": "application/json",
                "Authorization": f"Bearer {ACCESS_TOKEN}"
            }
        )

        print("Respuesta del servidor:", response.text)
        if response.status_code in (200, 201):
            print("✅ Dispositivo registrado exitosamente.")
            return True
        elif response.status_code == 401:
            print("Token expirado. Intentando refrescar...")
            if refrescar_token():
                return registrar_dispositivo()
            else:
                print("No se pudo refrescar el token.")
                return False
        else:
            print("Error al registrar dispositivo:", response.status_code)
            return False
    except Exception as e:
        print("Error al registrar el dispositivo:", e)
        return False


def generar_medicion():
    return {
        "soilMoisture": round(random.uniform(0, 100), 1),
        "soilIluminance": round(random.uniform(0, 100), 1),
        "environmentTemperature": round(random.uniform(15, 35), 1),
        "environmentMoisture": round(random.uniform(30, 90), 1),
        "erosion": 0.0,
        "dateTime": obtener_fecha_iso(),
        "device": {
            "type": "esp32",
            "id": obtener_mac(),
            "client": {"cc": 1234567890}
        },
    }


def enviar_medicion(medicion):
    try:
        response = requests.post(
            URL_MEASUREMENTS_SOIL,
            json=medicion,
            headers={
                "Content-Type": "application/json",
                "Authorization": "Bearer " + ACCESS_TOKEN
            }
        )
        if response.status_code == 401:
            print("Token expirado. Intentando refrescar...")
            if refrescar_token():
                return enviar_medicion(medicion)
            else:
                print("No se pudo refrescar el token.")
                return False
        else:
            print("📤 Servidor respondió:", response.text)
            return True
    except Exception as e:
        print("Error enviando datos:", e)
        return False
