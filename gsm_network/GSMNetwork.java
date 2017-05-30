import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

public class GSMNetwork {
    private final InputReader reader;
    private final OutputWriter writer;

    public GSMNetwork(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new GSMNetwork(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertGSMNetworkProblemToSat {
        int numVertices;
        Edge[] edges;

        ConvertGSMNetworkProblemToSat (int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
        }

        void printEquisatisfiableSatFormula() {
	   // prints number of variables & number of clauses 
	   int V = 3*numVertices;
	   int C = numVertices + 3*numVertices + 3*edges.length;
           writer.printf(V + " " + C + " 0\n");
	   // keep printing 3 sets of formulas 
	   // (xi1 | xi2 | xi3)
	   for(int i = 0; i < numVertices; i++) 
		writer.printf((3*i+1) + " " + (3*i+2) + " " + (3*i+3) + " 0\n"); 
	   // (xi1 | xi2) (xi2 | xi3) (xi1 | xi3)
	   for(int i = 0; i < numVertices; i++) { 
		writer.printf(-1*(3*i+1) + " " + -1*(3*i+2) + " 0\n"); 
		writer.printf(-1*(3*i+2) + " " + -1*(3*i+3) + " 0\n"); 
		writer.printf(-1*(3*i+3) + " " + -1*(3*i+1) + " 0\n");
	   }
	   // (xi1 | xj1) (xi2 | xj2) (xi3 | xj3)
	   for(int k = 0; k < edges.length; k++) {
		int i = edges[k].from, j = edges[k].to;
		writer.printf(-1*(3*(i-1)+1) + " " + -1*(3*(j-1)+1) + " 0\n"); 
		writer.printf(-1*(3*(i-1)+2) + " " + -1*(3*(j-1)+2) + " 0\n"); 
		writer.printf(-1*(3*(i-1)+3) + " " + -1*(3*(j-1)+3) + " 0\n");
	   } 
	         
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertGSMNetworkProblemToSat  converter = new ConvertGSMNetworkProblemToSat (n, m);
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
