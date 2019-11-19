package com.lagacione.faculdademarotinhaapi.resources;

import com.lagacione.faculdademarotinhaapi.domain.Materia;
import com.lagacione.faculdademarotinhaapi.dto.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.dto.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.services.MateriaService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/materia")
public class MateriaResource {
    @Autowired
    private MateriaService materiaService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<MateriaDTO>> findAll() {
        return ResponseEntity.ok().body(this.materiaService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<MateriaDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="25") Integer size,
            @RequestParam(value="orderBy", defaultValue="name") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        return ResponseEntity.ok().body(this.materiaService.findPage(page, size, orderBy, direction));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Materia> find(@PathVariable Integer id) throws ObjectNotFoundException {
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
