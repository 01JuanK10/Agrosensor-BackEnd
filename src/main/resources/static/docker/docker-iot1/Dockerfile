FROM python:3.11-slim

WORKDIR /app

COPY . .

# Instalar dependencias
RUN pip install --no-cache-dir -r requirements.txt

# Ejecutar el simulador
CMD ["python", "main.py"]
