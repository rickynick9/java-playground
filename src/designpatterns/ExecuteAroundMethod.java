package designpatterns;

import java.io.Closeable;
import java.util.function.Consumer;

class Resource {

    public Resource() {
        System.out.println("Resource created !");
    }

    public Resource op1() {
        System.out.println("OP1 ...");
        return this;
    }

    public Resource op2() {
        System.out.println("OP2 ...");
        return this;
    }

    public void close() {
        System.out.println("Release external resources ...");
    }
}

class ResourceT {

    public ResourceT() {
        System.out.println("Resource created !");
    }

    public ResourceT op1() {
        System.out.println("OP1 ...");
        return this;
    }

    public ResourceT op2() {
        System.out.println("OP2 ...");
        return this;
    }

    public void close() {
        System.out.println("Release external resources ...");
    }
}

class ResourceARM implements Closeable {

    public ResourceARM() {
        System.out.println("Resource created !");
    }

    public ResourceARM op1() {
        System.out.println("OP1 ...");
        return this;
    }

    public ResourceARM op2() {
        System.out.println("OP2 ...");
        return this;
    }

    public void close() {
        System.out.println("Release external resources ...");
    }
}

class ResourceEA {
    // ResourceEA - Resource execute around
    private ResourceEA() {
        System.out.println("Resource created !");
    }

    public ResourceEA op1() {
        System.out.println("OP1 ...");
        return this;
    }

    public ResourceEA op2() {
        System.out.println("OP2 ...");
        return this;
    }

    private void close() {
        System.out.println("Release external resources ...");
    }

    public static void use(Consumer<ResourceEA> block) {
        ResourceEA resourceEA = new ResourceEA(); // Before
        try {
            block.accept(resourceEA);
        } finally {
            resourceEA.close(); // After
        }
    }
}

public class ExecuteAroundMethod {

    // Remove the burden of object allocation and de-allocation from user

    public static void main(String[] args) {

        Resource resource = new Resource();
        resource.op1()
                .op2()
                .close(); // What if there was an exception while closing. Not a good idea of closing resource ourselves.
        // We did not call close(). So external resources are not released

        ResourceT resourceT = new ResourceT();
        try {
            resourceT.op1()
                    .op2();
        } finally {
            resourceT.close();
        }
        //This code is verbose
        // And its easy to forget to call try and finally

        // ARM - automatic resource management, try with resources
        try(ResourceARM resourceARM = new ResourceARM()) {
            resourceARM.op1()
                    .op2();
        }
        //This code is not as verbose.
        // If you forget try(), resources will not be released

        ResourceEA.use(r -> r.op1().op2());

        // This is more civilized AOP.

        //https://bazlur.com/2021/06/lets-remove-the-boilerplate-code-with-execute-around-pattern/
        //Execute around thread safety aspect in List



    }
}
