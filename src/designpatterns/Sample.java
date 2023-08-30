package designpatterns;

// Close a hierarchy
// Imaging you are creating an application and provide others to write plugins that can be executed in your application
// If you are providing some interfaces to connect with your application, what if they start implementing interface that they
// are not supposed to implement
// If you crete an interface, anyone can implement it, how do you say here is an interface that can be implemented but others
// can only use it.

// You are creating classes but you want classes to have closed hierarchy. This is where sealed shines realy well.
// You can define an interface for use-only purpose but not for inheritance. People creating plugins for your application
// can use interfaces to interact with your application but if they go on to create their own implementation the compiler
// will give them an error saying "sorry you are not supposed to implement that interface, you are only supposed to use it."




// Design Patterns Revisited in Modern Java by Venkat Subramaniam
// Parallel and Asynchronous Programming with Streams and CompletableFuture with Venkat Subramaniam

public class Sample {
}
