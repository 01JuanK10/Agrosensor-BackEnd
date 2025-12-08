import time
import json
from conexion_internet import obtener_mac
from fechas import sincronizar_hora
from peticiones_api import registrar_dispositivo, registrar_usuario_dispositivo, generar_medicion, enviar_medicion


def main():
    print("Simulador de dispositivo ESP32 virtual iniciado.")
    sincronizar_hora()

    if not registrar_usuario_dispositivo():
        print("No se pudo registrar el usuario. Abortando.")
        return

    if registrar_dispositivo():
        print("Dispositivo registrado. Iniciando envío de mediciones...\n")
        while True:
            medicion = generar_medicion()
            print("\n--- Medición simulada ---")
            print(json.dumps(medicion, indent=2))
            enviar_medicion(medicion)
            print("------------------------\n")
            time.sleep(30)
    else:
        print("\nNo se ha podido registrar el dispositivo")


if __name__ == "__main__":
    main()
