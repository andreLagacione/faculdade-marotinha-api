package com.lagacione.faculdademarotinhaapi.aluno.endpoint;

import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoCursoListaDTO;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoListaDTO;
import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.aluno.service.AlunoService;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
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

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<AlunoListaDTO>> findAll() {
        return ResponseEntity.ok().body(this.alunoService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public Page<AlunoListaDTO> findPage(@PageableDefault(page = 0, size = 25) Pageable pageable) {
        return this.alunoService.findPage(pageable);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<AlunoCursoListaDTO> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.alunoService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody AlunoDTO alunoDTO) throws Exception {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.alunoService.salvarRegistro(alunoDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Aluno adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetornoDTO> update(
            @Valid @RequestBody AlunoDTO alunoDTO
    ) throws Exception {
        this.alunoService.salvarRegistro(alunoDTO, false);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Aluno editado com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetornoDTO> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.alunoService.delete(id);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Aluno removido com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
