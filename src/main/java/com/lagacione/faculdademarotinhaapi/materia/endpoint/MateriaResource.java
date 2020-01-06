package com.lagacione.faculdademarotinhaapi.materia.endpoint;

import com.lagacione.faculdademarotinhaapi.commons.models.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.materia.service.MateriaService;
import javassist.tools.rmi.ObjectNotFoundException;
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
@RequestMapping(value="/materia")
public class MateriaResource {
    private MateriaService materiaService;

    @Autowired
    public void MateriaResource(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping(value="/combo-list")
    public List<MateriaDTO> findAll() {
        return this.materiaService.findAll();
    }

    @PostMapping(value = "/list")
    public Page<MateriaDTO> findPage(Pageable pageable) {
        return this.materiaService.findPage(pageable);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public MateriaDTO find(@PathVariable Integer id) throws ObjectNotFoundException {
        return this.materiaService.find(id);
    }

    @PostMapping
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody MateriaDTO materiaDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.materiaService.salvarRegistro(materiaDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Matéria adicionada com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @PutMapping
    public PadraoMensagemRetornoDTO update(
            @Valid @RequestBody MateriaDTO materiaDTO
    ) throws ObjectNotFoundException {
        this.materiaService.salvarRegistro(materiaDTO, false);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Matéria editada com sucesso!");
    }

    @DeleteMapping(value="/{id}")
    public PadraoMensagemRetornoDTO delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.materiaService.delete(id);
        return new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Matéria removida com sucesso!");
    }
}
