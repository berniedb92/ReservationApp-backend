/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.bernie.prenotazione.webservice;

/**
 *
 * @author Bernardino Di Biagio
 */
public class ControlloCodiceFiscale {

        // Toglie gli spazi vuoti da una stringa che si presume
        // rappresenti un numero di codice fiscale,
        // ne converte le minuscole in maiuscole, infine verifica
        // che sia corretto. Prendiamo come riferimento la regole di
        // composizione di un codice fiscale descritte alla URL
        //    https://it.wikipedia.org/wiki/Codice_fiscale
        // ma le implementiamo con qualche semplificazione, ad esempio
        // senza preoccuparci delle 'X' di riempimento per le triple
        // che codificano cognome e nome e senza preoccuparci che il
        // carattere di controllo sia accurato.

    public static String minMaj( String min ){
    
        // Toglie gli spazi vuoti da una String che rappresenta
        // un (presunto) codice fiscale e ne trasforma le minuscole
        // in maiuscole.

        if ( min == null ) return null ;
                
        String maj = "" ; char ch ;
        
        for( int i = 0 ; i < min.length() ; i++ )

            if ( ( ch = min.charAt( i ) ) != ' ' &&  ch != '\t' )

                if ( ch >= 'a' && ch <= 'z' )

                    maj = maj + (char)( ch - 'a' + 'A' ) ;
                    
                else
                    maj = maj + ch ;
                    
        return maj;
    }
    
    public static boolean codiceFiscale( String cf ){
    
        // Implementazione provvisoria di un riconoscitore di codici fiscali:
        // esempi corretti sono: MDOGNE51B25G702P e FRTPLA46M41G224S
        // ma se nel primo di questi due mettessimo '0' al posto di 'O'
        // oppure sostituissimo "GEN" a "GNE", allora diventerebbe scorretto.

        if ( cf == null ) return false ;
    
        return cf.length() == 16
        
            && cognome( cf.substring( 0, 3 ) )
        
            && nome( cf.substring( 3, 6 ) )
            
            && data_nascita_e_sesso( cf.substring( 6, 11 ) )
            
            && comune_o_stato_nascita( cf.substring( 11, 15 ) )
            
            && char_ctrl( cf.charAt( 15 ), cf )
        ;
        
    }
    
    public static boolean nome( String nom ){

// accerta che 'cog' sia costituito
// da consonanti seguite da vocali
    
        return cognome( nom ) ;   
    }
    
    public static boolean cognome( String cog ){

// accerta che 'cog' sia costituito
// da consonanti seguite da vocali

// Esercizio: sistemare in modo che tenga conto anche di eventuali
// 'X' di riempimento finali

        boolean conson = true ; // mi aspetto una consoante
                
        for( int i = 0 ; i < cog.length( ); i = i + 1 ){
        
            char ch = cog.charAt( i ) ;
        
            if ( conson && ! consonante( ch ) )
            
               conson = false; // d'ora in poi mi aspetto una vocale
               
            if ( ! conson && ! vocale( ch ) ) return false ;   
        }
            
        return true ;        
    }
    
    public static boolean lettera( char ch ){
    
        return ch >= 'A' && ch <= 'Z';
    
    }

    public static boolean vocale( char ch ){
    
        return "AEIOU".indexOf( ch ) != -1 ;
    }

    public static boolean consonante( char ch ){
    
        return lettera( ch ) && ! vocale( ch ) ;
    }
    
    public static boolean data_nascita_e_sesso( String data_gen ){
    
        int gio ; // giorno del mese combinato con il genere ( 'femmina' +40 )
    
        return cifre( data_gen.substring( 0, 2 ) )
        
            && "ABCDEHLMPRST".indexOf( data_gen.charAt( 2 ) ) != -1 // mese
            
            && cifre( data_gen.substring( 3 ) )
            
            && ((gio = Integer.parseInt( data_gen.substring( 3, 5 ) )) <= 31 && gio > 0 
            
                ||  gio <= 71 && gio > 40 ) ;
    }
    
    public static boolean cifre( String cc ){ // riconosce una sequenza di sole cifre
    
        for( int i = 0 ; i < cc.length( ); i = i + 1 ){
        
            char ch = cc.charAt( i ) ;
        
            if ( ch < '0' || ch > '9' ) return false ;
        }
            
        return true ;
    }

    public static boolean comune_o_stato_nascita( String comune ){ // codice Belfiore
    
        return lettera( comune.charAt( 0 ) ) && cifre( comune.substring( 1 ) );  
    }

    public static boolean char_ctrl( char let , String  codice ){
    
    // Esercizio: Completare la verifica che il carattere di controllo sia
    // conforme alle regole del codice fiscale.
    
        return lettera( let ) ;
    
    }
    
//    public static void main( String[] aa ){ // programma principale, uso COLLAUDO
//    
//        String cf ;
//        
//        do {
//            cf = Leggi.leggi( "Codice Fiscale" );
//            
//            System.out.println( cf );
//            
//            Leggi.emettiMessaggio( "CF=" + minMaj( cf ) );
//            System.out.println( "CF=" + minMaj( cf ) );
//            
//            Leggi.emettiMessaggio( codiceFiscale( minMaj( cf ) ) ? "OK" : "????" );
//            System.out.println( codiceFiscale( minMaj( cf ) ) ? "OK" : "????" );
//        }
//        while( cf != null );
//        
//        Leggi.emettiMessaggio( "Ciao, a presto :)" );
//         
//    } 
}
