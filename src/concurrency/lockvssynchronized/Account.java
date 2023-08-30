package concurrency.lockvssynchronized;

/*

https://www.techiedelight.com/recursion-practice-problems-with-solutions/

=========================================== SYNCHRONIZE ======================================
In Java, we have two constructs to acquire locks—the archaic synchronized and its modern counterpart, the Lock interface.
The Lock interface gives us more control for locking than the synchronized keyword.

We use synchronized to gain an explicit monitor/lock on objects. While grabbing the monitor and releasing it at the
end of the block, it also helps threads cross the memory barrier.

However, synchronized is very primitive in its capabilities and has quite a few limitations.

Unfortunately, synchronized makes it possible to indefinitely block to acquire a lock. This is because there’s no easy way
to tell synchronized to wait for a finite time to acquire a lock.

synchronized leads to exclusive locks, and no other thread can gain access to the monitors held. This does not favor
situations with multiple readers and infrequent writers. Even though the readers could run concurrently, they’re serialized,
and this results in poor concurrent performance.

=========================================== LOCK ======================================

The Java 5 Lock interface gives us better control than the synchronized keyword.

We acquire and release locks using the Lock interface’s lock() and unlock() methods, like so:
aMonitor.lock();
try {
  //...
} finally {
  aMonitor.unlock();
}

We perform the unlock() in the finally block to ensure a proper unlock even in the case of exceptions.
Although lock() is a blocking call, we can call the nonblocking variation tryLock() to instantly acquire the lock
if available. To wait for a finite time, provide a timeout parameter to the tryLock() method.

With the tryLock() method, our lock requests are not forced to block.
Instead, we can check instantly whether we’ve acquired the lock.
Also, if we decide to wait for a lock, we can place a limit on the wait time.
So, there is no indefinite waiting as in case of synchronize keyword.

We can acquire concurrent read locks and exclusive write locks using ReadWriteLock.
Thus, multiple readers can continue concurrently without having to wait and are delayed only when a conflicting writer
is active.

ReentrantLock -
As the name suggests, it allows threads to re-request locks they already own, thus allowing them to reenter their
mutually exclusive sections.


Memory Barrier :
Changes made by one thread may not be visible to other threads since the data could be residing in local working memory
of a thread instead of main memory. In order to make it visible, Memory Barrier is needed. “Memory Barrier” means copying
data from local working memory to main memory.

A change made by one thread is guaranteed to be visible to another thread only if the writing thread crosses the
memory barrier and then the reading thread crosses the memory barrier. synchronized and volatile keywords force that the
changes are globally visible on a timely basis; these help cross the memory barrier.

 */

/*

If two threads try to transfer money between two accounts concurrently but in the opposite order, like so:
thread1: transfer money from account1 to account2
thread2: transfer money from account2 to account1

then in this case thread1 may lock account1, and simultaneously thread2 may lock account2. The two thread are now
deadlocked, waiting to acquire a lock on the other account they need, while holding their current locks.

We can prevent this by making the two threads request locks on accounts in the same order. Then, one of them can
proceed to acquire both the locks. The other thread can then follow after a temporary block, instead of being held
by a deadlock.

The Account class implements the Comparable interface to facilitate this natural order for locking

 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account implements Comparable<Account> {

    private int balance;
    public final Lock monitor = new ReentrantLock();

    public Account(final int initialBalance) {
        balance = initialBalance;
    }

    public int compareTo(final Account other) {
        return Integer.compare(hashCode(), other.hashCode());
    }
    public void deposit(final int amount) {
        monitor.lock();
        try {
            if (amount > 0) balance += amount;
        } finally { //In case there was an Exception we're covered
            monitor.unlock();
        }
    }
    public boolean withdraw(final int amount) {
        try {
            monitor.lock();
            if(amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            monitor.unlock();
    }
    }
}

