
def partition(arr, start, end):
    pivot = start;
    # for each variable, compare it to whats the compare item at the end
    for i in range(start, end):
        if arr[i] < arr[end]:
            swap(arr, i, pivot)
            pivot += 1

    swap(arr, pivot, end)
    return pivot

def swap(arr, a, b):
    tmp = arr[a]
    arr[a] = arr[b]
    arr[b] = tmp

def quick_sort(arr, start, end):
    if start >= end:
        return
    # partition
    pivot = partition(arr, start, end)
    # quick sort left
    quick_sort(arr, start, pivot - 1)
    # quick sort right
    quick_sort(arr, pivot + 1, end)

input = [5, 2, 3, 7, 1, 4, 6]
quick_sort(input, 0, len(input) - 1)
print(input)