package com.lagacione.faculdademarotinhaapi.turma.endpoint;

import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.turma.model.*;
import com.lagacione.faculdademarotinhaapi.turma.service.TurmaService;
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
@RequestMapping(value="/turma")
public class TurmaResource {
    private TurmaService turmaService;

    @Autowired
    public void TurmaResource(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping(value="/combo-list")
    public List<TurmaComboListDTO> findAll() {
        return this.turmaService.findAll();
    }

    @PostMapping(value = "/list")
    public Page<TurmaListDTO> findPage(@RequestBody TurmaFilter filter, Pageable pageable) {
        return this.turmaService.findPage(pageable, filter);
    }

    @GetMapping(value="/{id}")
    public TurmaEditDTO find(@PathVariable Integer id) throws ObjectNotFoundException {
        return this.turmaService.find(id);
    }

    @PostMapping
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody TurmaDTO turmaDTO) throws Exception {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.turmaService.salvarRegistro(turmaDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Turma adicionada com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @PutMapping
    public PadraoMensagemRetornoDTO update(
            @Valid @RequestBody TurmaDTO turmaDTO
    ) throws Exception {
        this.turmaService.salvarRegistro(turmaDTO, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Turma editada com sucesso!");
    }

    @DeleteMapping(value="/{id}")
    public PadraoMensagemRetornoDTO delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.turmaService.delete(id);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Turma removida com sucesso!");
    }
}
