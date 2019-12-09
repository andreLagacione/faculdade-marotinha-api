package com.lagacione.faculdademarotinhaapi.resources;

import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestreDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestreListDTO;
import com.lagacione.faculdademarotinhaapi.dto.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.service.MateriaNotaBimestreService;
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
public class MateriaNotaBimestreResource {
    @Autowired
    private MateriaNotaBimestreService materiaNotaBimestreService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<MateriaNotaBimestreListDTO>> findAll() {
        return ResponseEntity.ok().body(this.materiaNotaBimestreService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<MateriaNotaBimestreListDTO>> findPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.materiaNotaBimestreService.findPage(pageable));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<MateriaNotaBimestreDTO> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.materiaNotaBimestreService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(
            @Valid @RequestBody MateriaNotaBimestreDTO materiaNotaBimestreDTO
    ) throws Exception {
        URI uri;
        PadraoMensagemRetornoDTO mensagemRetorno;

        uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        this.materiaNotaBimestreService.salvarRegistro(materiaNotaBimestreDTO, true).getId()
                ).toUri();

        mensagemRetorno = new PadraoMensagemRetornoDTO(
                HttpStatus.CREATED,
                HttpStatus.valueOf("CREATED").value(),
                "Nota adicionada com sucesso!"
        );

        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetornoDTO> update(
            @Valid @RequestBody MateriaNotaBimestreDTO materiaNotaBimestreDTO
    ) throws Exception {
        this.materiaNotaBimestreService.salvarRegistro(materiaNotaBimestreDTO, false);
        PadraoMensagemRetornoDTO mensagemRetorno;

        mensagemRetorno = new PadraoMensagemRetornoDTO(
                HttpStatus.OK,
                HttpStatus.valueOf("OK").value(),
                "Nota editada com sucesso!"
        );

        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetornoDTO> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.materiaNotaBimestreService.delete(id);
        PadraoMensagemRetornoDTO mensagemRetorno;

        mensagemRetorno = new PadraoMensagemRetornoDTO(
                HttpStatus.OK,
                HttpStatus.valueOf("OK").value(),
                "Nota removida com sucesso!"
        );

        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
