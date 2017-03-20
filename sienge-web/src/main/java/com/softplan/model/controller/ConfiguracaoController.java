package com.softplan.model.controller;

import com.softplan.model.bean.ConfiguracaoBean;
import com.softplan.model.util.WebUtil;
import com.softplan.model.entidade.Configuracao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("configuracaoController")
@SessionScoped
public class ConfiguracaoController implements Serializable {

    @EJB
    private ConfiguracaoBean configuracaoBean;
    private List<Configuracao> lista = null;
    private Configuracao selecionado;

    public Configuracao getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Configuracao selecionado) {
        this.selecionado = selecionado;
    }

    public ConfiguracaoController() {
    }

    public Configuracao novaConfiguracao() {
        selecionado = new Configuracao();
        return selecionado;
    }

    public void salvar() {
        try {
            configuracaoBean.salvar(selecionado);
            WebUtil.addMsgSucesso("Registro salvo com sucesso!");
            if (!WebUtil.isValido()) {
                lista = null;
            }
            selecionado = null;

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao salvar!");
        }
    }

    public void remover() {
        try {
            configuracaoBean.remover(selecionado);
            if (!WebUtil.isValido()) {
                selecionado = null;
                lista = null;
            }
            WebUtil.addMsgSucesso("Removido com sucesso!");
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            WebUtil.addMsgErro("Erro ao remover!");
        }
    }

    public List<Configuracao> getListaConfiguracoes() {
        if (lista == null) {
            lista = configuracaoBean.listarTodos();
        }
        return lista;
    }

}
