package com.collab.g5.demo.exceptions.companies;

public class CompaniesNotFoundException extends RuntimeException {

    public CompaniesNotFoundException(int cid){
        super("Could not find company id: "+ cid);
    }
    public CompaniesNotFoundException(String name){
        super("Could not find company email: "+ name);
    }
    public CompaniesNotFoundException(){
        super("No companies found");
    }
}
