
from collections import deque
def rabin_karp_match(input, match):
    naive_hash_value = 0
    for c in match:
        naive_hash_value += ord(c)

    curr_hash_value = 0
    queue = deque()
    match_start_idx = set()
    for i in range(0, len(input)):
        curr_hash_value += ord(input[i])
        queue.append(ord(input[i]))
        if len(queue) < len(match):
            continue
        elif len(queue) == len(match):
            if (curr_hash_value == naive_hash_value):
                match_start_idx.add(i + 1 - len(match))
        else:
            left = queue.popleft()
            curr_hash_value -= left
            if (curr_hash_value == naive_hash_value):
                match_start_idx.add(i + 1 - len(match))


    for i in match_start_idx:
        if input[i:i+len(match)] == match:
            print('found match {}'.format(match))

rabin_karp_match('abcdefdsgsccdbaae', 'bcd')
rabin_karp_match('abcdef', 'ge')