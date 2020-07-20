def increment_count(char_count, c):
    if c in char_count:
        char_count[c] = char_count[c] + 1
    else:
        char_count[c] = 1

def decrement_count(char_count, c):
    if c in char_count:
        char_count[c] = char_count[c] - 1
    else:
        char_count[c] = -1

def check_count(char_count):
    for v in char_count.values():
        if v != 0:
            return False

    return True

def find_anagram(input, anagram):
    char_count = {}
    for i in range(len(anagram)):
        increment_count(char_count, anagram[i])

    for i in range(len(input)):
        decrement_count(char_count, input[i])
        if i < len(anagram):
            continue
        increment_count(char_count, input[i - len(anagram)])
        if check_count(char_count):
            print('found anagram {}'.format(input[i + 1 - len(anagram):i+1]))



find_anagram('abcdasdfadfcbadsf', 'cb')