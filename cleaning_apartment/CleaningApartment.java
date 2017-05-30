import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class CleaningApartment {
    private final InputReader reader;
    private final OutputWriter writer;

    public CleaningApartment(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new CleaningApartment(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertHampathToSat {
        int numVertices;
        Edge[] edges;

        ConvertHampathToSat(int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
        }

        void printEquisatisfiableSatFormula() {
		/*
            writer.printf("3 2\n");
            writer.printf("1 2 0\n");
            writer.printf("-1 -2 0\n");
            writer.printf("1 -2 0\n");
		*/
	    int V = numVertices*numVertices + numVertices*(numVertices-1)/2;
	    int C = numVertices + numVertices*numVertices*(numVertices-1)/2;
	    writer.printf(V + " " + C + " 0\n");
	    //For all i (xi1 | xi2 | ….|xin)
	    for(int i = 0; i < numVertices; i++) {
		StringBuffer buf = new  StringBuffer(); 
	    	for(int j = 1; j <= numVertices; j++)
			buf.append((numVertices*i + j) + " "); 
		buf.append("0\n");
		writer.printf(buf.toString());
	    }
	    // For i, j,k (xij | xik)
	    for(int i = 0; i < numVertices; i++) {
	    	for(int j = 1; j <= numVertices; j++)
	    		for(int k = j+1; k <= numVertices; k++)
				writer.printf(-1*(numVertices*i+j) + " " + -1*(numVertices*i+k) + " 0\n");
	    }
	    
             // edges clause
	     int offset = numVertices*numVertices;
	     for(int i = 0; i < edges.length; i++) {
		int s = edges[i].from;
		int d = edges[i].to;
                writer.printf((offset + numVertices*s+d) + " 0\n");
                writer.printf((offset + numVertices*d+s) + " 0\n");

	     }
	     // xij  | xkj+1  | eik 
	     for(int j = 1; j < numVertices; j++)
		for(int i = 0; i < numVertices; i++)
			for(int k = i+1; k < numVertices; k++) 
				writer.printf(-1*(numVertices*i+j) +  " " + -1*(numVertices*k+j+1) + (offset+ numVertices*i+k)+ " 0\n"); 
	     
	    
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertHampathToSat converter = new ConvertHampathToSat(n, m);
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = reader.nextInt();
            converter.edges[i].to = reader.nextInt();
        }

        converter.printEquisatisfiableSatFormula();
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}
