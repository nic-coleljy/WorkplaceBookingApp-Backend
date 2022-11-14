package com.collab.g5.demo.exceptions.companies;

public class InsertCompanyException extends RuntimeException{
    public InsertCompanyException(int cid) { super("This "+ cid+ " already taken"); }
}
