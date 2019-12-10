package com.lagacione.faculdademarotinhaapi.materia.endpoint;

import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.commons.PadraoMensagemRetornoDTO;
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

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<MateriaDTO>> findAll() {
        return ResponseEntity.ok().body(this.materiaService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public Page<MateriaDTO> findPage(Pageable pageable) {
        return this.materiaService.findPage(pageable);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<MateriaDTO> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.materiaService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody MateriaDTO materiaDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.materiaService.salvarRegistro(materiaDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Matéria adicionada com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetornoDTO> update(
            @Valid @RequestBody MateriaDTO materiaDTO
    ) throws ObjectNotFoundException {
        this.materiaService.salvarRegistro(materiaDTO, false);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Matéria editada com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetornoDTO> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.materiaService.delete(id);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Matéria removida com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
