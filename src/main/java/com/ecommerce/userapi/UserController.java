package com.ecommerce.userapi;

import com.ecommerce.userapi.domain.User;
import com.ecommerce.userapi.domain.UserDTO;
import com.ecommerce.userapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{cpf}")
    public UserDTO getUserByCpf(@PathVariable String cpf) {

        var user = userRepository.findByCpf(cpf);

        if (user != null) {
            return new UserDTO(user);
        } else {
            return null;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> search(
            @PageableDefault(size = 10, sort = { "name" }) //altera as configurações padrão de paginação e ordenação do Spring
            Pageable pageable, @RequestParam(name="name", required = true) String name) {

        var page = userRepository.findByNameLike(pageable, name)
                .map(UserDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> list(
            @PageableDefault(size = 10, sort = { "name" }) //altera as configurações padrão de paginação e ordenação do Spring
            Pageable pageable) {
        var page = userRepository.findAllByActiveTrue(pageable) //retorna uma lista de médicos
                .map(UserDTO::new); //instancia um obj de listagem e envia o list de médicos para o seu construtor
        return ResponseEntity.ok(page); //retorna conteudo e código 200
    }

    @PutMapping
    public ResponseEntity update(
            @RequestBody //recebe os dados do corpo da requisição
            @Valid //executa as validações do bean validation
            UserDTO userDTO) {

        var user = userRepository.findByCpf(userDTO.cpf());

        if (user != null) {
            user.update(userDTO);
            userRepository.save(user);
        } else {
            throw new RuntimeException();
        }

        return ResponseEntity.ok(new UserDTO(user));

    }

    @PostMapping
    public ResponseEntity create(
            @RequestBody //recebe os dados do corpo da requisição
            @Valid //executa as validações do bean validation
            UserDTO userDTO,
            UriComponentsBuilder uriBuilder) {

        var user = new User(userDTO);
        userRepository.save(user);

        //constroi a URI para o cabeçalho Location
        var uri = uriBuilder
                .path("/user/{cpf}")
                .buildAndExpand(user.getCpf())
                .toUri();

        return ResponseEntity
                .created(uri) //cria o cabeçalho location com o código 201
                .body(new UserDTO(user)); // devolve o conteudo do recurso criado

    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity delete(@PathVariable String cpf) {

        var user = userRepository.findByCpf(cpf);

        if (user != null) {
            user.delete();
        } else {
            throw new RuntimeException();
        }

        return ResponseEntity.noContent().build();
    }

}
