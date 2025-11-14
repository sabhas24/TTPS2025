# API REST - Sistema de Mascotas Perdidas

## Resumen de Implementación

Se ha implementado exitosamente una API REST completa utilizando **Spring Boot** con las mejores prácticas de **Spring Core**, incluyendo:

✅ **Inyección de dependencias por constructor** en todas las capas  
✅ **Arquitectura en capas**: Controller → Service → Repository  
✅ **DTOs** para separar la capa de presentación de la lógica de negocio  
✅ **Documentación con Swagger/OpenAPI**  

---

## 📋 Funcionalidades Implementadas

### 1. **Gestión de Usuarios**

#### **POST /usuarios** - Registrar Usuario
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "contraseña": "123456",
  "telefono": "2211234567",
  "barrio": "Centro",
  "ciudad": "La Plata",
  "foto": "https://example.com/foto.jpg"
}
```
**Respuesta**: 201 Created

#### **PUT /usuarios/{id}** - Editar Perfil de Usuario
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez",
  "telefono": "2211111111",
  "barrio": "Tolosa",
  "ciudad": "La Plata",
  "foto": "https://example.com/nueva-foto.jpg"
}
```
**Respuesta**: 200 OK

#### **POST /usuarios/login** - Login del Sistema
```json
{
  "email": "juan@example.com",
  "contraseña": "123456"
}
```
**Respuesta**: 200 OK (devuelve datos del usuario)

#### **DELETE /usuarios/{id}** - Deshabilitar Usuario
**Respuesta**: 204 No Content

---

### 2. **Gestión de Mascotas**

#### **POST /mascotas** - Crear Mascota
```json
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
**Respuesta**: 201 Created

#### **GET /mascotas/{id}** - Ver Mascota por ID
**Respuesta**: 200 OK

#### **PUT /mascotas/{id}** - Editar Mascota
```json
{
  "nombre": "Firulais",
  "color": "Marrón claro",
  "tamanio": "MEDIANO",
  "descripcionExtra": "Tiene collar azul y chip",
  "fotos": ["https://example.com/foto1.jpg", "https://example.com/foto2.jpg"],
  "coordenada": {
    "latitud": -34.9205,
    "longitud": -57.9536
  }
}
```
**Respuesta**: 200 OK

#### **DELETE /mascotas/{id}** - Eliminar (Deshabilitar) Mascota
**Respuesta**: 204 No Content

#### **GET /mascotas/perdidas** - Listar Mascotas Perdidas
**Respuesta**: 200 OK (array de mascotas en estado PERDIDO_PROPIO)

#### **GET /mascotas/usuario/{id}** - Ver Mascotas de un Usuario
**Respuesta**: 200 OK (array de mascotas del usuario)

#### **PATCH /mascotas/{id}/estado?estado=RECUPERADO** - Cambiar Estado
**Respuesta**: 200 OK

---

### 3. **Gestión de Avistamientos**

#### **POST /avistamientos** - Crear Avistamiento
```json
{
  "mascotaId": 1,
  "usuarioId": 2,
  "coordenada": {
    "latitud": -34.9215,
    "longitud": -57.9546
  },
  "comentario": "Vi a esta mascota en la plaza",
  "fotos": ["https://example.com/avistamiento1.jpg"],
  "enPosesion": false
}
```
**Respuesta**: 201 Created

#### **GET /avistamientos** - Listar Todos los Avistamientos
**Respuesta**: 200 OK

#### **GET /avistamientos/mascota/{mascotaId}** - Listar Avistamientos por Mascota
**Respuesta**: 200 OK

#### **GET /avistamientos/usuario/{usuarioId}** - Listar Avistamientos por Usuario
**Respuesta**: 200 OK

#### **GET /avistamientos/en-posesion** - Listar Avistamientos en Posesión
**Respuesta**: 200 OK

#### **DELETE /avistamientos/{id}** - Deshabilitar Avistamiento
**Respuesta**: 204 No Content

---

## 🏗️ Arquitectura Implementada

### **Capa Controller**
- `UsuarioController.java`
- `MascotaController.java`
- `AvistamientoController.java`

**Responsabilidad**: Endpoints REST, validación de entrada, códigos HTTP

### **Capa Service**
- `UsuarioService.java`
- `MascotaService.java`
- `AvistamientoService.java`

**Responsabilidad**: Lógica de negocio, validaciones, transformación DTO ↔ Entity

### **Capa Repository**
- `UsuarioRepository.java`
- `MascotaRepository.java`
- `AvistamientoRepository.java`

**Responsabilidad**: Acceso a datos con Spring Data JPA

### **DTOs**
```
DTOs/
├── UsuariosDTOs/
│   ├── UsuarioCreateRequestDTO.java
│   ├── UsuarioUpdateRequestDTO.java
│   ├── UsuarioLoginRequestDTO.java
│   └── UsuarioResponseDTO.java
├── MascotasDTOs/
│   ├── MascotaCreateRequestDTO.java
│   ├── MascotaUpdateRequestDTO.java
│   └── MascotaResponseDTO.java
└── AvistamientosDTOs/
    ├── AvistamientoCreateRequestDTO.java
    └── AvistamientoResponseDTO.java
```

---

## ✨ Mejores Prácticas Aplicadas

### 1. **Inyección de Dependencias por Constructor**
```java
@Service
public class MascotaService {
    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;
    
    public MascotaService(MascotaRepository mascotaRepository,
                          UsuarioRepository usuarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
    }
}
```

**Ventajas**:
- Dependencias inmutables (`final`)
- Más testeable (fácil inyectar mocks)
- No necesita `@Autowired`

### 2. **Separación de DTOs y Entidades**
- **Request DTOs**: Datos de entrada del cliente
- **Response DTOs**: Datos de salida al cliente
- **Entities**: Modelo de dominio interno

### 3. **Manejo de Errores con ResponseStatusException**
```java
Usuario usuario = usuarioRepository.findById(id)
    .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, 
        "Usuario no encontrado"
    ));
```

### 4. **Validaciones en Capa de Servicio**
```java
mascotaValidator.validar(mascota);
```

### 5. **Soft Delete (Deshabilitación)**
No se eliminan físicamente los registros, se marcan como `habilitado = false`

---

## 🚀 Cómo Probar la API

### Opción 1: Swagger UI
1. Inicia la aplicación
2. Abre el navegador: `http://localhost:8080/swagger-ui.html`
3. Explora y prueba los endpoints

### Opción 2: Postman/cURL
```bash
# Registrar usuario
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@example.com",
    "contraseña": "123456",
    "telefono": "2211234567",
    "barrio": "Centro",
    "ciudad": "La Plata"
  }'

# Listar mascotas perdidas
curl http://localhost:8080/mascotas/perdidas
```

---

## 📝 Notas Adicionales

- **Autenticación**: El login es básico (sin JWT ni Spring Security). Para producción, implementar autenticación segura.
- **Contraseñas**: Se almacenan en texto plano. En producción usar BCrypt o similar.
- **CORS**: Configurar según necesidades del frontend.
- **Validación**: Considerar agregar `@Valid` y `@NotNull` en DTOs.

---

## ✅ Checklist de Funcionalidades

- [x] Registración de Usuario
- [x] Edición del Perfil de Usuario
- [x] Login del sistema
- [x] Crear Mascota
- [x] Editar Mascota
- [x] Eliminar (deshabilitar) Mascota
- [x] Ver Mascota por ID
- [x] Listado de Mascotas perdidas
- [x] Crear Avistamiento
- [x] Listar Avistamientos

**🎉 ¡API REST completamente implementada con Spring Core!**
 q