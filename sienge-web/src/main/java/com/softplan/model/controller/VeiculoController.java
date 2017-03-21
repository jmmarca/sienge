package com.softplan.model.controller;

import com.softplan.model.entidade.Veiculo;
import com.softplan.model.util.WebUtil;
import com.softplan.model.bean.VeiculoBean;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("veiculoController")
@SessionScoped
public class VeiculoController implements Serializable {

    @EJB
    private VeiculoBean veiculoBean;
    private List<Veiculo> listaVeiculos = null;
    private Veiculo selecionado;

    public VeiculoController() {
    }

    public Veiculo getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Veiculo selecionado) {
        this.selecionado = selecionado;
    }

    public Veiculo novoVeiculo() {
        selecionado = new Veiculo();
        return selecionado;
    }

    public void salvar() {
        try {
            veiculoBean.salvar(selecionado);
            WebUtil.addMsgSucesso("Registro salvo com sucesso!");
            if (!WebUtil.isValido()) {
                listaVeiculos = null;
            }
            selecionado = null;

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao salvar!");
        }
    }

    public void remover() {
        try {
            veiculoBean.remover(selecionado);
            if (!WebUtil.isValido()) {
                selecionado = null;
                listaVeiculos = null;
            }
            WebUtil.addMsgSucesso("Removido com sucesso!");
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao remover!");
        }
    }

    public List<Veiculo> getListaVeiculos() {
        if (listaVeiculos == null) {
            listaVeiculos = veiculoBean.listarTodos();
        }
        return listaVeiculos;
    }

}
