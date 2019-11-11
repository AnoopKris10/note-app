package com.example.notesrepo;

public class DownloadModel {

    String Uri;
    String DocName;

    public DownloadModel(String doc_name, String uri) {
        this.DocName = doc_name;
        this.Uri=uri;

    }


    public String getUri() {
        return Uri;
    }


    public String getDocName() {
        return DocName;
    }

}
