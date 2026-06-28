package model;

public class FormulaComposicaoModel {
    private int idFormula, idInsumo, idLoteInsumo;
    private String dosagem;
    private String nomeInsumo, tipoInsumo;

    public int getIdFormula() { return idFormula; }
    public void setIdFormula(int idFormula) { this.idFormula = idFormula; }

    public int getIdInsumo() { return idInsumo; }
    public void setIdInsumo(int idInsumo) { this.idInsumo = idInsumo; }

    public int getIdLoteInsumo() { return idLoteInsumo; }
    public void setIdLoteInsumo(int idLoteInsumo) { this.idLoteInsumo = idLoteInsumo; }

    public String getDosagem() { return dosagem; }
    public void setDosagem(String dosagem) { this.dosagem = dosagem; }

    public String getNomeInsumo() { return nomeInsumo; }
    public void setNomeInsumo(String nomeInsumo) { this.nomeInsumo = nomeInsumo; }

    public String getTipoInsumo() { return tipoInsumo; }
    public void setTipoInsumo(String tipoInsumo) { this.tipoInsumo = tipoInsumo; }
}
