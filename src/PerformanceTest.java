import db.FileSystemDAO;
import db.FileSystemEntity;

public class PerformanceTest {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        FileSystemDAO dao = new FileSystemDAO();

        System.out.println("=== File System Performance Tests ===\n");

        // Test 1: Bulk Directory Creation
        test1_BulkDirectoryCreation(fs);

        // Test 2: Bulk File Creation
        test2_BulkFileCreation(fs);

        // Test 3: Deep Nested Structure
        test3_DeepNestedStructure(fs);

        // Test 4: Database Query Performance
        test4_DatabaseQueryPerformance(dao);

        // Test 5: List Operation Performance
        test5_ListPerformance(fs);

        System.out.println("=== All Tests Completed ===");
    }

    // Test 1: Create 1000 directories
    static void test1_BulkDirectoryCreation(FileSystem fs) {
        System.out.println("Test 1: Creating 1000 directories...");

        // First create parent directory
        fs.CreateDirectory("/test_dirs");

        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            fs.CreateDirectory("/test_dirs/dir_" + i);
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        double avgTime = duration / 1000.0;

        System.out.println("✓ Created 1000 directories in " + duration + "ms");
        System.out.println("✓ Average: " + String.format("%.2f", avgTime) + "ms per directory");
        System.out.println();
    }

    // Test 2: Create 500 files with content
    static void test2_BulkFileCreation(FileSystem fs) {
        System.out.println("Test 2: Creating 500 files with content...");
        fs.CreateDirectory("/test_files");

        long start = System.currentTimeMillis();

        for (int i = 0; i < 500; i++) {
            fs.CreateFile("/test_files/file_" + i + ".txt", "Sample content for file " + i);
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        double avgTime = duration / 500.0;

        System.out.println("✓ Created 500 files in " + duration + "ms");
        System.out.println("✓ Average: " + String.format("%.2f", avgTime) + "ms per file");
        System.out.println();
    }

    // Test 3: Deep nested directories (10 levels)
    static void test3_DeepNestedStructure(FileSystem fs) {
        System.out.println("Test 3: Creating 10-level deep nested structure...");
        long start = System.currentTimeMillis();

        String path = "/deep";
        fs.CreateDirectory(path);
        for (int i = 1; i <= 10; i++) {
            path += "/level" + i;
            fs.CreateDirectory(path);
        }
        fs.CreateFile(path + "/deepfile.txt", "File at level 10");

        long end = System.currentTimeMillis();
        System.out.println("✓ Created 10-level nested structure in " + (end - start) + "ms");
        System.out.println();
    }

    // Test 4: Database query performance
    static void test4_DatabaseQueryPerformance(FileSystemDAO dao) {
        System.out.println("Test 4: Testing database query performance (1000 lookups)...");
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            dao.getById(1); // Query root node
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        double avgTime = duration / 1000.0;

        System.out.println("✓ 1000 database queries in " + duration + "ms");
        System.out.println("✓ Average query time: " + String.format("%.2f", avgTime) + "ms");
        System.out.println();
    }

    // Test 5: List operation on large directory
    static void test5_ListPerformance(FileSystem fs) {
        System.out.println("Test 5: List operation on directory with 1000 items...");
        long start = System.currentTimeMillis();

        fs.List("/test_dirs");

        long end = System.currentTimeMillis();
        System.out.println("✓ Listed 1000 items in " + (end - start) + "ms");
        System.out.println();
    }
}