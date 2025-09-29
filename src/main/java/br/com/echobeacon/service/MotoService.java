package br.com.echobeacon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.echobeacon.model.Moto;
import br.com.echobeacon.repository.MotoRepository;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public Moto salvar(Moto moto) {
        if (motoRepository.findByPlaca(moto.getPlaca()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma moto com esta placa.");
        }
        if (motoRepository.findByChassi(moto.getChassi()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma moto com este chassi.");
        }
        return motoRepository.save(moto);
    }

    public List<Moto> listarTodos() {
        return motoRepository.findAll();
    }

    public Optional<Moto> buscarPorId(Long id) {
        return motoRepository.findById(id);
    }

    public void excluir(Long id) {
        motoRepository.deleteById(id);
    }
}