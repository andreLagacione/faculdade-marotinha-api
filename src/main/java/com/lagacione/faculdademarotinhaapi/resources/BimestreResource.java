package com.lagacione.faculdademarotinhaapi.resources;

import com.lagacione.faculdademarotinhaapi.domain.Bimestre;
import com.lagacione.faculdademarotinhaapi.dto.BimestreDTO;
import com.lagacione.faculdademarotinhaapi.dto.PadraoMensagemRetornoDTO;
import com.lagacione.faculdademarotinhaapi.services.BimestreService;
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
@RequestMapping(value="/bimestre")
public class BimestreResource {
    @Autowired
    private BimestreService bimestreService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<BimestreDTO>> findAll() {
        return ResponseEntity.ok().body(this.bimestreService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<BimestreDTO>> findPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.bimestreService.findPage(pageable));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Bimestre> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.bimestreService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetornoDTO> insert(@Valid @RequestBody BimestreDTO bimestreDTO) throws Exception {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.bimestreService.salvarRegistro(bimestreDTO, true).getId()).toUri();
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Bimestre adicionado com sucesso!");
        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetornoDTO> update(
            @Valid @RequestBody BimestreDTO bimestreDTO
    ) throws Exception {
        this.bimestreService.salvarRegistro(bimestreDTO, false);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Bimestre editado com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetornoDTO> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.bimestreService.delete(id);
        PadraoMensagemRetornoDTO mensagemRetorno = new PadraoMensagemRetornoDTO(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Bimestre removido com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
