package com.Biblioteca.Service;

import com.Biblioteca.DTO.Persona.*;
import com.Biblioteca.Exceptions.BadRequestException;
import com.Biblioteca.Models.Persona.Cliente;
import com.Biblioteca.Models.Persona.Persona;
import com.Biblioteca.Models.Persona.Usuario;
import com.Biblioteca.Models.Roles.Roles;
import com.Biblioteca.Models.Ubicacion.*;
import com.Biblioteca.Repository.Persona.ClienteRepository;
import com.Biblioteca.Repository.Persona.PersonaRepository;
import com.Biblioteca.Repository.Persona.UsuarioRepository;
import com.Biblioteca.Repository.RolesRepository;
import com.Biblioteca.Repository.Ubicacion.*;
import com.Biblioteca.Security.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class PersonaService implements UserDetailsService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CantonRepository cantonRepository;

    @Autowired
    private ParroquiaRepository parroquiaRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private BarrioRepository barrioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public PersonaClienteResponse registrarCliente(PersonaClienteRequest personaClienteRequest){
        Optional<Persona> optionalPersona = personaRepository.findByCedula(personaClienteRequest.getCedula());
        if(!optionalPersona.isPresent()){
            Optional<Persona> optionalPersonaEmail = personaRepository.findByEmail(personaClienteRequest.getEmail());
            if(!optionalPersonaEmail.isPresent()){
                Persona newPersona = new Persona();
                newPersona.setCedula(personaClienteRequest.getCedula());
                newPersona.setApellidos(personaClienteRequest.getApellidos());
                newPersona.setNombres(personaClienteRequest.getNombres());
                newPersona.setEmail(personaClienteRequest.getEmail());
                newPersona.setTelefono(personaClienteRequest.getTelefono());
                Persona persona = personaRepository.save(newPersona);
                if(persona!=null){
                    guardarCliente(persona.getCedula(), personaClienteRequest.getFechaNacimiento(), personaClienteRequest.getEstadoCivil(),
                            personaClienteRequest.getGenero(), personaClienteRequest.getDiscapacidad(), personaClienteRequest.getIdBarrio(),
                            personaClienteRequest.getIdCanton(), personaClienteRequest.getIdParroquia(), personaClienteRequest.getIdProvincia());
                    Optional<Cliente> cliente = clienteRepository.findByPersona(persona);
                    Optional<Canton> canton = cantonRepository.findById(personaClienteRequest.getIdCanton());
                    Optional<Parroquia> parroquia = parroquiaRepository.findById(personaClienteRequest.getIdParroquia());
                    Optional<Provincia> provincia = provinciaRepository.findById(personaClienteRequest.getIdProvincia());
                    return new PersonaClienteResponse(persona.getId(),persona.getCedula(),
                            persona.getApellidos(), persona.getNombres(),cliente.get().getFechaNacimiento(),
                            cliente.get().getEdad(),cliente.get().getGenero(), persona.getTelefono(), persona.getEmail(), cliente.get().getEstadoCivil(), cliente.get().isDiscapacidad(),
                            personaClienteRequest.getBarrio(),parroquia.get().getParroquia(), canton.get().getCanton(), provincia.get().getProvincia());

                }else {
                    log.error("No se puedo guardar el cliente con cédula: {} y email: {}", personaClienteRequest.getCedula(), personaClienteRequest.getEmail());
                    throw new BadRequestException("No se pudo guardar el cliente");
                }


            }else {
                //log.error("La cédula ya está registrada: {}", personaClienteRequest.getCedula());
                throw new BadRequestException("El email ingresado, ya esta registrado");
            }
        }else {
            //log.error("La cédula ya está registrada: {}", personaClienteRequest.getCedula());
            throw new BadRequestException("La cédula ingresada, ya está registrada");
        }
    }


    private boolean guardarCliente (String cedula, Date fechaNa, String estado, String genero, Boolean discapacidad, Long idBarrio, Long idCanton, Long idParroquia, Long idProvincia ){
        Optional<Persona> optionalPersona = personaRepository.findByCedula(cedula);
        if(optionalPersona.isPresent()){
            Persona persona = optionalPersona.get();
            Cliente newCliente = new Cliente();

            newCliente.setFechaNacimiento(fechaNa);
            newCliente.setEdad(edad(fechaNa));
            newCliente.setEstadoCivil(estado);
            newCliente.setGenero(genero);
            newCliente.setDiscapacidad(discapacidad);
            newCliente.setUbicacion(guardarUbicacion(idBarrio, idCanton, idParroquia, idProvincia));
            newCliente.setPersona(persona);
            Cliente cliente = clienteRepository.save(newCliente);
            if(cliente!=null){
                return true;
            }else{
                throw new BadRequestException("Cliente no registrado");

            }
        }else{
            throw new BadRequestException("La cedula ingresada, no está registrada");

        }

    }

    public Long edad (Date fechaNacimiento){
        Period edadC = Period.between(LocalDate.of(fechaNacimiento.getYear(),fechaNacimiento.getMonth(), fechaNacimiento.getDay()), LocalDate.now());
        int años =edadC.getYears()-1900;
        System.out.println("AÑOS"+años);
        return Long.parseLong(años+"");
    }

    private Ubicacion guardarUbicacion(Long idBarrio, Long idCanton, Long idParroquia, Long idProvincia){
        Optional<Barrio> optionalBarrio = barrioRepository.findById(idBarrio);
        if(optionalBarrio.isPresent()){
            Optional<Canton> optionalCanton = cantonRepository.findById(idCanton);
            if(optionalCanton.isPresent()){
                Optional<Parroquia> optionalParroquia = parroquiaRepository.findById(idParroquia);
                if(optionalParroquia.isPresent()){
                    Optional<Provincia> optionalProvincia = provinciaRepository.findById(idProvincia);
                    if (optionalProvincia.isPresent()) {
                        Optional<Ubicacion> optionalUbicacion=  ubicacionRepository.findByBarrioAndCantonAndParroquiaAndProvincia(optionalBarrio.get(),
                                optionalCanton.get(), optionalParroquia.get(), optionalProvincia.get());
                        if (!optionalUbicacion.isPresent() ) {
                            Ubicacion newUbicacion = new Ubicacion();
                            newUbicacion.setBarrio(optionalBarrio.get());
                            newUbicacion.setCanton(optionalCanton.get());
                            newUbicacion.setParroquia(optionalParroquia.get());
                            newUbicacion.setProvincia(optionalProvincia.get());
                            Ubicacion ubicacion = ubicacionRepository.save(newUbicacion);
                            if(ubicacion!=null){
                                return ubicacion;
                            }else{
                                throw new BadRequestException("Ubicacion no registrada");
                            }
                        }else{
                            return optionalUbicacion.get();
                        }

                    }else{
                        throw new BadRequestException("No existe una provincia con id" +idProvincia);
                    }
                }else{
                    throw new BadRequestException("No existe una parroquia con id" +idParroquia);
                }
            }else{
                throw new BadRequestException("No existe un canton con id" +idCanton);
            }
        }else{
            throw new BadRequestException("No existe un barrio con id" +idBarrio);
        }

    }


    @Transactional
    public PersonaUsuarioResponse registrarUsuario(PersonaUsuarioRequest personaUsuarioRequest) throws Exception {
        Optional<Persona> optionalPersona = personaRepository.findByEmail(personaUsuarioRequest.getEmail());
        if(!optionalPersona.isPresent()) {
            Persona newPersona = new Persona();
            newPersona.setCedula(personaUsuarioRequest.getCedula());
            newPersona.setApellidos(personaUsuarioRequest.getApellidos());
            newPersona.setNombres(personaUsuarioRequest.getNombres());
            newPersona.setTelefono(personaUsuarioRequest.getTelefono());
            newPersona.setEmail(personaUsuarioRequest.getEmail());
            if (!getPersona(personaUsuarioRequest.getCedula())) {
                Persona persona = personaRepository.save(newPersona);
                if (persona != null) {
                    guardarUsuario(persona.getCedula(), personaUsuarioRequest.getClave(), personaUsuarioRequest.getIdRol());
                    Optional<Usuario> user = usuarioRepository.findByPersona(persona);
                    return new PersonaUsuarioResponse(persona.getId(), persona.getCedula(),
                            persona.getApellidos(), persona.getNombres(), persona.getEmail(),
                             persona.getTelefono(), user.get().getClave(), user.get().getRoles().getId(),generateTokenSignUp(personaUsuarioRequest));

                }else {
                    log.error("No se puedo guardar el usuario con cédula: {} e email: {}", personaUsuarioRequest.getCedula(), personaUsuarioRequest.getEmail());
                    throw new BadRequestException("No se pudo guardar el usuario");
                }
            }else {
                log.error("La cédula ya está registrada: {}", personaUsuarioRequest.getCedula());
                throw new BadRequestException("La cedula ingresada, ya esta registrada, si la cedula le pertenece contactenos a");
            }
        }else {
            throw new BadRequestException("El email ingresado, ya esta registrado");
        }
    }

    private boolean guardarUsuario(String cedula,String clave,Long idRol){
        Optional<Persona> optionalPersona = personaRepository.findByCedula(cedula);
        if(optionalPersona.isPresent()){
            Optional<Roles> optionalRoles= rolesRepository.findById(idRol);
            if(optionalRoles.isPresent()){
            Persona persona = optionalPersona.get();
            Usuario newUsuario = new Usuario();
            newUsuario.setClave(clave);
            newUsuario.setPersona(persona);
            newUsuario.setRoles(optionalRoles.get());
            Usuario user = usuarioRepository.save(newUsuario);
            if(user!=null){
                return true;
            }else{
                throw new BadRequestException("Usuario no registrado");
            }

            }else{
                throw new BadRequestException("El rol seleccionado no existe");
            }

        }else{
            throw new BadRequestException("La cedula ingresada, no está registrada");
        }
    }

    private boolean getPersona(String cedula) {
        return personaRepository.existsByCedula(cedula);
    }


    public PersonaUsuarioResponse login (UsuarioRequest usuarioRequest) throws Exception {
        Optional<Persona> optional = personaRepository.findByEmail(usuarioRequest.getEmail());
        if(optional.isPresent()){
            Optional<Usuario> usuarioOptional= usuarioRepository.findByPersona(optional.get());
            if(usuarioOptional.isPresent()){
                if(usuarioRequest.getClave().equals(usuarioOptional.get().getClave())){
                    return new PersonaUsuarioResponse(optional.get().getId(),optional.get().getCedula(),
                            optional.get().getApellidos(), optional.get().getNombres(), optional.get().getEmail(),
                            optional.get().getTelefono(), usuarioOptional.get().getClave(), usuarioOptional.get().getRoles().getId(),
                            generateTokenLogin(usuarioRequest));
                }else{
                    throw new BadRequestException("Contraseña incorrecta para email: " + usuarioRequest.getEmail());
                }
            }else{
                log.info("EMAIL NO EXISTE");
                throw new BadRequestException("Usuario no registrado como usuario");
            }
        }else{
            log.info("EMAIL NO EXISTE");
            throw new BadRequestException("Usuario no registrado");
        }
    }
        @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Persona> usuario = personaRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(usuario.get().getEmail(), usuario.get().getEmail(), new ArrayList<>());
    }

    public String generateTokenLogin(UsuarioRequest userRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getEmail())
            );
        } catch (Exception ex) {
            log.error("INVALID: error al generar token en login de usuario con email: {}", userRequest.getEmail());
            throw new Exception("INAVALID");
        }
        return jwtUtil.generateToken(userRequest.getEmail());
    }

    public String generateTokenSignUp(PersonaUsuarioRequest registerRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getEmail())
            );
        } catch (Exception ex) {
            log.error("INVALID: error al generar token en signup de usuario con email: {}", registerRequest.getEmail());
            throw new BadRequestException("INAVALID");
        }
        return jwtUtil.generateToken(registerRequest.getEmail());
    }
}
