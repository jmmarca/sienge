package com.softplan.model.controller;

import com.github.adminfaces.template.session.AdminSession;
import com.softplan.model.bean.EntidadeBean;
import com.softplan.model.bean.UsuarioBean;
import com.softplan.model.entidade.Usuario;
import com.softplan.model.util.WebUtil;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.inject.Specializes;
import javax.faces.bean.ManagedProperty;
import org.omnifaces.util.Faces;

/**
 *
 * @author Jean Michél Marca
 */
@Named
@SessionScoped
@Specializes
public class LoginController extends AdminSession implements Serializable {

    @EJB
    private UsuarioBean usuarioBean;

    @EJB
    private EntidadeBean entidadeBean;

    @ManagedProperty(value = "#{usuario}")
    private Usuario usuario;

    private String login;

    private String senha;

    public LoginController() {
    }

    public void verificaSessao() throws IOException {
        if (usuario == null) {
            setIsLoggedIn(false);
            Faces.redirect("login.xhtml");
        }
    }

    public void efetuarLogin() {
        try {
            final int contar = usuarioBean.contar();
            if (contar == 0) {
                entidadeBean.inicializarDB();
            }
            usuario = usuarioBean.logar(usuario, senha);
            if (usuario == null) {
                WebUtil.addMsgErro("Usuário ou senha inválido");
                setIsLoggedIn(false);
            } else {
                setIsLoggedIn(true);
                Faces.getExternalContext().getFlash().setKeepMessages(true);
                Faces.redirect("index.xhtml");
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            WebUtil.addMsgErro("Erro ao Logar");
        }
    }

    public void logout() throws IOException {
        usuario = null;
        setIsLoggedIn(false);
        Faces.getExternalContext().getFlash().setKeepMessages(true);
        Faces.redirect("login.xhtml");
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
