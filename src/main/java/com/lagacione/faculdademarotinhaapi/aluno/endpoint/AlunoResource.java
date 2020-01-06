package com.lagacione.faculdademarotinhaapi.aluno.endpoint;

import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoForEditDTO;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoListaDTO;
import com.lagacione.faculdademarotinhaapi.aluno.service.AlunoService;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/aluno")
public class AlunoResource {
    private AlunoService alunoService;

    @Autowired
    public void AlunoResource(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping(value="/combo-list")
    public List<AlunoListaDTO> findAll() {
        return this.alunoService.findAll();
    }

    @PostMapping(value = "/list")
    public Page<AlunoListaDTO> findPage(@PageableDefault(page = 0, size = 25) Pageable pageable) {
        return this.alunoService.findPage(pageable);
    }

    @GetMapping(value="/{id}")
    public AlunoForEditDTO find(@PathVariable Integer id) throws ObjectNotFoundException {
        return this.alunoService.find(id);
    }

    @PostMapping
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody AlunoDTO alunoDTO) throws ActionNotAllowedException {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.alunoService.salvarRegistro(alunoDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Aluno adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @PutMapping
    public PadraoMensagemRetornoDTO update(
            @Valid @RequestBody AlunoDTO alunoDTO
    ) throws ActionNotAllowedException {
        this.alunoService.salvarRegistro(alunoDTO, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Aluno editado com sucesso!");
    }

    @DeleteMapping(value="/{id}")
    public PadraoMensagemRetornoDTO delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.alunoService.delete(id);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Aluno removido com sucesso!");
    }
}
