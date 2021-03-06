/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jenasparql;

import java.util.Iterator;

/**
 *
 * @author lala
 */
public class JenaSparQL {

    static String ns = "urn:si2:ej2"; //namespace
   
    public static void main(String[] args) {
        LogCtrl.setCmdLogging();
        
        
        Model scheme = RDFdataMgr.loadModel("example.ttl");
        Model data = RDFDAtaMgr.loadModel("datos.ttl");
        
        //Create an instace of Reasonator
        InfModel inferencia = ModelFactory.createRDFSModel(scheme, data);
        
        StmtIterator iter;
        iter = inferencia.listStatements();
        System.out.println("----------------");
        while(iter.hasNext()){
            Statement statement = iter.nextStatement();
            System.out.println("--->" PrintUtil.print(statement));
        }
        
        Resource juan = inferencia.getResource(ns+"juan");
        iter = inferencia.listStatements(juan, RDF.Type,(RDFNode)null);
        
        System.out.println("----------------");
        while(iter.hasNext()){
            Statement statement = iter.nextStatement();
            System.out.println("--->" PrintUtil.print(statement));
        }
        
        Reasoner reasoner = ReasonerRegistry.getRDFSSimpleReasoner();
        InfModel inference = ModelFactory.createInfModel(reasoner, scheme,data);
        
        StmtIterator iter;
        Resource juan = inferencia.getResource(ns+"juan");
        iter = inferencia.listStatements(juan, RDF.Type,(RDFNode)null);
        while(iter.hasNext()){
            Statement statement = iter.nextStatement();
            System.out.println("--->" PrintUtil.print(statement));
        }
        
        //Check for inconsistences
        ValidityReport validacion = inferencia.validate();
        
        if(validacion.isValid()){
            System.out.println("OK!");
        }
        else{
            for(Iterator interv = validacion.getReports(); interv.hasNext();){
                System.out.println(interv.next());
            }
        }
        
    }
    
}
