public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("参数" + (i+1) + ":" + args[i]);
        }
        System.out.println("-Xmx" + Runtime.getRuntime().maxMemory()/1024/1024+"M");
        System.out.println("-Xmx" + Runtime.getRuntime().maxMemory());
    }
}

