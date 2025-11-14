# Guía Rápida: Cómo Probar la API REST

## 📋 Pre-requisitos

1. **Iniciar la aplicación Spring Boot**
2. Asegurarte que está corriendo en `http://localhost:8080`

---

## 🎯 Método 1: Swagger UI (MÁS FÁCIL)

### Pasos:
1. Inicia tu aplicación
2. Abre el navegador
3. Ve a: **`http://localhost:8080/swagger-ui.html`**
4. Verás la interfaz de Swagger con todos tus endpoints
5. Haz clic en cualquier endpoint → "Try it out" → "Execute"

**¡No necesitas escribir código ni instalar nada!**

---

## 💻 Método 2: PowerShell (Script incluido)

He creado un script de prueba: **`test-api.ps1`**

### Uso:
```powershell
# 1. Inicia tu aplicación Spring Boot

# 2. Ejecuta el script desde PowerShell:
cd C:\Users\SATO\IdeaProjects\TTPS2025
.\test-api.ps1
```

El script probará automáticamente:
- ✅ Registrar usuario
- ✅ Login
- ✅ Crear mascota
- ✅ Listar mascotas perdidas
- ✅ Crear avistamiento
- ✅ Listar avistamientos

---

## 🧪 Método 3: Comandos individuales con cURL

### Registrar Usuario
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/usuarios" -Method Post -Body '{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "contraseña": "123456",
  "telefono": "2211234567",
  "barrio": "Centro",
  "ciudad": "La Plata"
}' -ContentType "application/json"
```

### Login
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/usuarios/login" -Method Post -Body '{
  "email": "juan@example.com",
  "contraseña": "123456"
}' -ContentType "application/json"
```

### Listar Mascotas Perdidas
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/mascotas/perdidas" -Method Get
```

### Crear Mascota
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/mascotas" -Method Post -Body '{
  "nombre": "Firulais",
  "color": "Marrón",
  "tamanio": "MEDIANO",
  "descripcionExtra": "Tiene collar azul",
  "fotos": ["https://example.com/foto1.jpg"],
  "coordenada": {
    "latitud": -34.9205,
    "longitud": -57.9536
  },
  "publicadorId": 1
}' -ContentType "application/json"
```

### Obtener Mascota por ID
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/mascotas/1" -Method Get
```

### Editar Mascota
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/mascotas/1" -Method Put -Body '{
  "nombre": "Firulais",
  "color": "Marrón claro",
  "tamanio": "GRANDE",
  "descripcionExtra": "Actualizado",
  "fotos": ["https://example.com/foto2.jpg"],
  "coordenada": {
    "latitud": -34.9205,
    "longitud": -57.9536
  }
}' -ContentType "application/json"
```

### Crear Avistamiento
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/avistamientos" -Method Post -Body '{
  "mascotaId": 1,
  "usuarioId": 1,
  "coordenada": {
    "latitud": -34.9215,
    "longitud": -57.9546
  },
  "comentario": "Vi a esta mascota en la plaza",
  "fotos": ["https://example.com/avistamiento1.jpg"],
  "enPosesion": false
}' -ContentType "application/json"
```

### Listar Avistamientos
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/avistamientos" -Method Get
```

---

## 📱 Método 4: Postman

1. **Descarga Postman**: https://www.postman.com/downloads/
2. **Crea una nueva colección** llamada "API Mascotas"
3. **Agrega requests** para cada endpoint
4. **Importa** esta colección (próximo archivo)

---

## 🔍 Método 5: IntelliJ IDEA HTTP Client

IntelliJ tiene un cliente HTTP integrado. Crea un archivo `.http`:

```http
### Registrar Usuario
POST http://localhost:8080/usuarios
Content-Type: application/json

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "contraseña": "123456",
  "telefono": "2211234567",
  "barrio": "Centro",
  "ciudad": "La Plata"
}

### Login
POST http://localhost:8080/usuarios/login
Content-Type: application/json

{
  "email": "juan@example.com",
  "contraseña": "123456"
}

### Listar Mascotas Perdidas
GET http://localhost:8080/mascotas/perdidas

### Crear Mascota
POST http://localhost:8080/mascotas
Content-Type: application/json

{
  "nombre": "Firulais",
  "color": "Marrón",
  "tamanio": "MEDIANO",
  "descripcionExtra": "Tiene collar azul",
  "fotos": ["https://example.com/foto1.jpg"],
  "coordenada": {
    "latitud": -34.9205,
    "longitud": -57.9536
  },
  "publicadorId": 1
}
```

---

## ✅ Checklist de Pruebas

### Usuarios
- [ ] POST /usuarios - Registrar
- [ ] PUT /usuarios/{id} - Editar perfil
- [ ] POST /usuarios/login - Login
- [ ] DELETE /usuarios/{id} - Deshabilitar

### Mascotas
- [ ] POST /mascotas - Crear
- [ ] GET /mascotas/{id} - Obtener por ID
- [ ] PUT /mascotas/{id} - Editar
- [ ] DELETE /mascotas/{id} - Eliminar
- [ ] GET /mascotas/perdidas - Listar perdidas
- [ ] GET /mascotas/usuario/{id} - Por usuario
- [ ] PATCH /mascotas/{id}/estado - Cambiar estado

### Avistamientos
- [ ] POST /avistamientos - Crear
- [ ] GET /avistamientos - Listar todos
- [ ] GET /avistamientos/mascota/{id} - Por mascota
- [ ] GET /avistamientos/usuario/{id} - Por usuario
- [ ] GET /avistamientos/en-posesion - En posesión
- [ ] DELETE /avistamientos/{id} - Deshabilitar

---

## 🚨 Solución de Problemas

### "Connection refused"
→ Tu aplicación no está corriendo. Iníciala primero.

### "404 Not Found"
→ Verifica la URL del endpoint

### "500 Internal Server Error"
→ Revisa los logs de la aplicación

### "400 Bad Request"
→ Verifica el formato JSON del body

---

## 🎯 Recomendación

**Para empezar: USA SWAGGER UI** 
- Es lo más fácil
- No requiere instalación
- Documenta automáticamente
- Interfaz visual intuitiva

Solo abre: `http://localhost:8080/swagger-ui.html`

