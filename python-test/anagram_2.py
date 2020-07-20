
def check_count(char_count):
    for v in char_count.values():
        if v != 0:
            return False
    return True


def has_anagram(input, anagram):
    char_count = {}
    for c in anagram:
        if c in char_count:
            char_count[c] = char_count[c] + 1
        else:
            char_count[c] = 1

    for i in range(len(input)):
        c = input[i]
        if c in char_count:
            char_count[c] = char_count[c] - 1
        else:
            char_count[c] = -1

        if i < len(anagram):
            continue
        elif i >= len(anagram):
            c = input[i - len(anagram)]
            if c in char_count:
                char_count[c] = char_count[c] + 1
            else:
                char_count[c] = 1
        if check_count(char_count):
            print('found anagram at {}'.format(i + 1 - len(anagram)))


has_anagram('abcdefg', 'bc')
has_anagram('abcdefg', 'eg')

