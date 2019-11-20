package com.lagacione.faculdademarotinhaapi.resources;

import com.lagacione.faculdademarotinhaapi.domain.MateriaNota;
import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaDTO;
import com.lagacione.faculdademarotinhaapi.dto.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.services.MateriaNotaService;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
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
@CrossOrigin(origins = "*")
@RequestMapping(value="/nota")
public class MateriaNotaResource {
    @Autowired
    private MateriaNotaService materiaNotaService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<MateriaNotaDTO>> findAll() {
        return ResponseEntity.ok().body(this.materiaNotaService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<MateriaNotaDTO>> findPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.materiaNotaService.findPage(pageable));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<MateriaNota> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.materiaNotaService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody MateriaNotaDTO materiaNotaDTO) throws Exception {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.materiaNotaService.salvarRegistro(materiaNotaDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Nota adicionada com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetornoDTO> update(
            @Valid @RequestBody MateriaNotaDTO materiaNotaDTO
    ) throws Exception {
        this.materiaNotaService.salvarRegistro(materiaNotaDTO, false);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Nota editada com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetornoDTO> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.materiaNotaService.delete(id);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Nota removida com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
