class Node:
    def __init__(self, val):
        self.val = val
        self.next = None

    def __str__(self):
        return str(self.val)

def print_linked_list(node):
    curr = node
    while curr:
        print(curr, end=" ")
        curr = curr.next

def reverse_linked_list(node):
    prev = None
    curr = node
    while curr:
        tmp = curr.next
        curr.next = prev
        prev = curr
        curr = tmp

node1 = Node(1)
node2 = Node(2)
node3 = Node(3)
node4 = Node(4)

node1.next = node2
node2.next = node3
node3.next = node4

print_linked_list(node1)
print()
reverse_linked_list(node1)
print_linked_list(node1)
print()
print_linked_list(node4)
