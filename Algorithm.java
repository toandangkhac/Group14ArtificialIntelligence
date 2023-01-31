import java.util.*;
import java.io.*;



public class Algorithm{
    private static List<Vertex> vertexList;
    
    public static void main(String[] ar){
        vertexList = new ArrayList<Vertex>();
        readFile(vertexList);
        List<String> solution = depthLimitedSearch("B", "F", 3);// start, end, limited
        // List<String> solution = iterativeDeepeningSearch("B", "F");// start, end
        showSolution(solution);
    }
    public static void showSolution(List<String> solution){
        if(solution.size() == 0){
            System.out.println("Not find path.");
        }
        else{
            int stt = 0;
            for(String a: solution){
                if(stt == 0){
                    System.out.print(a);
                }
                else{
                    System.out.print(" -> " + a);
                }
                stt++;
            }
        }
        writeFile(solution);
    }
    public static List<String> depthLimitedSearch(String nodeStart, String nodeEnd, int limited){
        Stack<Vertex> stackVertex = new Stack<Vertex>();
        List<String> solution = new ArrayList<String>();
        boolean cutoff = true;
        int l = 0;
        for(Vertex x: vertexList){
            x.resetVisited();
        }
        for(Vertex x: vertexList){// find vertex start
            if(x.getName().equals(nodeStart)){
                x.setVisited();
                stackVertex.push(x);
                
                while(!stackVertex.isEmpty()){
                    Vertex temp = stackVertex.peek();// lay gia tri de kiem tra dinh ke
                    if(temp.getName().equals(nodeEnd)){
                        cutoff = false;
                        break;
                    }
                    for(String a: temp.getAdj()){// duyet cac dinh ke xem da duoc tham chua
                        boolean haveAVertexNotVisited = false;
                        for(Vertex b: vertexList){
                            if( b.getName().equals(a) && !b.isVisited() && l < limited ){
                                l++;
                                b.setVisited();
                                stackVertex.push(b);
                                haveAVertexNotVisited = true;
                                break;
                            }
                        }
                        if(haveAVertexNotVisited){
                            break;
                        }
                        else{
                            haveAVertexNotVisited = false;
                            for(String c: temp.getAdj()){
                                for(Vertex k: vertexList){
                                    if(k.getName().equals(c) &&  !k.isVisited() && l < limited){
                                        haveAVertexNotVisited = true;
                                        break;
                                    }
                                }
                                if(haveAVertexNotVisited){
                                    break;
                                }
                            }
                            if(haveAVertexNotVisited){
                                continue;
                            }
                            else{
                                stackVertex.pop();
                                l--;
                                break;
                            }
                        }
                    }
                }
                if(!cutoff){
                    for(Vertex c : stackVertex){
                        solution.add(c.getName());
                    }

                }
                else{
                    // System.out.println("CUTOFF");
                }
                break;
            }
        }
        return solution;
    }
    
    
    public static List<String> iterativeDeepeningSearch(String nodeStart, String nodeEnd){
        List<String> solution = new ArrayList<String>();
        int i = 0;
        while(i < vertexList.size()){
            solution = depthLimitedSearch(nodeStart, nodeEnd,i);
            if(!solution.isEmpty()){
                break;
            }
            i++;
        }
        return solution;
    }
    public static void readFile(List<Vertex> v){
        try{
            FileReader fr = new FileReader("input.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while(true){
                line = br.readLine();
                if(line == null){
                    break;
                }
                String[] component = line.split(" ");
                String v1 = component[0];
                boolean v1IsExist = false;
                boolean v2IsExist = false;

                String v2 = component[1];
                for(Vertex ver: v){
                    if(ver.isExist(v1)){
                        v1IsExist = true;
                        ver.addAdjacencyVertex(v2);
                        continue;
                    }
                    if(ver.isExist(v2)){
                        v2IsExist = true;
                        ver.addAdjacencyVertex(v1);
                        continue;
                    }
                }
                if(!v1IsExist){
                    Vertex temp = new Vertex(v1);
                    temp.addAdjacencyVertex(v2);
                    v.add(temp);
                }
                if(!v2IsExist){
                    Vertex temp = new Vertex(v2);
                    temp.addAdjacencyVertex(v1);
                    v.add(temp);
                }

            }
            br.close();
            fr.close();
        }catch (Exception e){

        }
    }
    public static void writeFile(List<String> solution){
        try{
            FileWriter fw = new FileWriter("output.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            if(solution.isEmpty()){
                bw.write("Not find path.");
            }
            else{
                int i = 0;
                for(String a: solution){
                    if(i == 0){
                        bw.write(a);
                    }
                    else{
                        bw.write(" -> " + a);
                    }
                    i++;
                } 
                
            }
            bw.close();
            fw.close();
        }catch (Exception e){

        }
    }

}





public class Vertex{
    private String name;
    private List<String> adj;
    private boolean visited;
    public Vertex(String n){
        visited = false;
        name = n;
        adj = new ArrayList<String>();
    }
    public void addAdjacencyVertex(String n){
        adj.add(n);
    }
    public boolean isVisited(){
        return visited;
    }
    public void setVisited(){
        visited = true;
    }
    public void resetVisited(){
        visited = false;
    } 
    public boolean isExist(String v){
        if(v.equals(name)){
            return true;
        }
        return false;
    }
    public String getName(){
        return name;
    }
    public String getAdjacencyVertex(){
        return adj.toString();
    }
    public List<String> getAdj(){
        return adj;
    }
}