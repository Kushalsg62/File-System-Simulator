public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        System.out.println("Creating structure...");
        fs.CreateDirectory("/docs");
        fs.CreateDirectory("/docs/projects");
        fs.CreateFile("/docs/projects/todo.txt", "Finish MySQL integration");
        fs.CreateFile("/docs/readme.txt", "This is the docs folder");

        System.out.println("\nListing /docs:");
        fs.List("/docs");

        System.out.println("\nRemoving /docs/readme.txt");
        fs.RemoveFile("/docs/readme.txt");

        System.out.println("\nListing /docs again:");
        fs.List("/docs");
    }
}
