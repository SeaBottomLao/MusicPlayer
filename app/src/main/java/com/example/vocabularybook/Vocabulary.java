package com.example.vocabularybook;

public class Vocabulary
{
    private int id;
    private String eng;
    private String chn;
    private String sample;

    public Vocabulary(int id, String eng, String chn, String sample)
    {
        this.id = id;
        this.eng = eng;
        this.chn = chn;
        this.sample = sample;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getChn() {
        return chn;
    }

    public void setChn(String chn) {
        this.chn = chn;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }
}
