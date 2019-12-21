package com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.endpoint;

import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestreDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestreListDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.service.MateriaNotaBimestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/nota")
public class MateriaNotaBimestreResource {
    private MateriaNotaBimestreService materiaNotaBimestreService;

    @Autowired
    public void MateriaNotaBimestreResource(MateriaNotaBimestreService materiaNotaBimestreService) {
        this.materiaNotaBimestreService = materiaNotaBimestreService;
    }

    @GetMapping(value="/combo-list")
    public List<MateriaNotaBimestreListDTO> findAll() {
        return this.materiaNotaBimestreService.findAll();
    }

    @GetMapping
    public Page<MateriaNotaBimestreListDTO> findPage(Pageable pageable) {
        return this.materiaNotaBimestreService.findPage(pageable);
    }

    @GetMapping(value="/{id}")
    public MateriaNotaBimestreDTO find(@PathVariable Integer id) throws ObjectNotFoundException {
        return this.materiaNotaBimestreService.find(id);
    }

    @PostMapping
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(
            @Valid @RequestBody MateriaNotaBimestreDTO materiaNotaBimestreDTO
    ) throws Exception {
        URI uri;
        uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.materiaNotaBimestreService.salvarRegistro(materiaNotaBimestreDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Nota adicionada com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @PutMapping
    public PadraoMensagemRetornoDTO update(
            @Valid @RequestBody MateriaNotaBimestreDTO materiaNotaBimestreDTO
    ) throws Exception {
        this.materiaNotaBimestreService.salvarRegistro(materiaNotaBimestreDTO, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Nota editada com sucesso!");
    }

    @DeleteMapping(value="/{id}")
    public PadraoMensagemRetornoDTO delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.materiaNotaBimestreService.delete(id, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Nota removida com sucesso!");
    }
}
