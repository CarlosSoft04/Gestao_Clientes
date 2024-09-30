package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

public class Backup {
    private static final SimpleDateFormat DATA_HORA = new SimpleDateFormat("ddMMyyyy_HHmmss");

    private String pathAbsolutoParcial() {
        File file = new File("backup.java");
        String pathAbsolutoAtual = file.getAbsolutePath();
        String pathAbsolutoParcial = pathAbsolutoAtual.substring(0, pathAbsolutoAtual.lastIndexOf(File.separator));
        return pathAbsolutoParcial;
    }

    public ArrayList<String> listarArquivos() {
        String pathDiretorio = pathAbsolutoParcial();
        File diretorio = new File(pathDiretorio);
        
        ArrayList<String> arquivosBackups = new ArrayList<>();
        
        if (diretorio.exists()) {
            File[] arquivosDiretorio = diretorio.listFiles();
            
            if (arquivosDiretorio != null && arquivosDiretorio.length > 0) {
                for (File arquivo : arquivosDiretorio) {
                    if (arquivo.isFile() && arquivo.getName().contains("backup")) {
                        arquivosBackups.add(arquivo.getAbsolutePath());
                    }
                }
            }
        }
        
        return arquivosBackups;
    }

    public void gerarBackup() {
        String pathDiretorio = pathAbsolutoParcial();
        String zipPath = pathDiretorio + File.separator + "backup" + DATA_HORA.format(new Date()) + ".zip";

        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            
            File resourcesDir = new File(pathDiretorio, "resources");
            adicionarAoZip("", resourcesDir.getAbsolutePath(), zos);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(null, "BackUp gerado com sucesso");
    }

    private void adicionarAoZip(String caminhoZip, String diretorioPath, ZipOutputStream zip) throws IOException {
        File diretorio = new File(diretorioPath);

        for (File file : diretorio.listFiles()) {
            String nomeArquivo = file.getName();
            String caminhoCompletoArquivo = diretorioPath + File.separator + nomeArquivo;

            if (file.isDirectory()) {
                adicionarAoZip(caminhoZip + nomeArquivo + File.separator, caminhoCompletoArquivo, zip);
            } else {
                ZipEntry zipEntry = new ZipEntry(caminhoZip + nomeArquivo);
                zip.putNextEntry(zipEntry);

                try (FileInputStream fileInputStream = new FileInputStream(caminhoCompletoArquivo)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                        zip.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }
    
    public void RestaurarBackup(String caminhoArquivoZip)throws FileNotFoundException, IOException {
    	byte[] buffer = new byte[1024];
    	
    	try(ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(caminhoArquivoZip))) {
    		ZipEntry zipEntry;
    		
    		while((zipEntry = zipInputStream.getNextEntry())!=null) {
    			String nomeArquivo = zipEntry.getName();
    			File arquivo = new File(pathAbsolutoParcial()+"\\resources" + File.separator + nomeArquivo );
    			
    			if(zipEntry.isDirectory()) {
    				arquivo.mkdirs();
    				continue;
    				
    			}
    			File parent = arquivo.getParentFile();
    			
    			if(!parent.exists()) {
    				parent.mkdirs();
    				
    			}
    			
    			try(FileOutputStream fileutput = new FileOutputStream(arquivo)) {
    				int i;
    				while((i=zipInputStream.read(buffer))>0) {
    					fileutput.write(buffer,0,i);
    					
    				}
					
				} 
				}
    		JOptionPane.showMessageDialog(null, "BackUp restaurado com sucesso");
    			}
    		
    	}
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

 