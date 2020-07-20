
def rabin_karpe_match(input, matcher):
    naive_hash = 0
    for c in matcher:
        naive_hash += ord(c)

    curr_hash = 0
    start_idx_match = []
    for i in range(len(input)):
        curr_hash += ord(input[i])

        if i < len(matcher):
            continue
        elif i >= len(matcher):
            curr_hash -= ord(input[i - len(matcher)])

        if curr_hash == naive_hash:
            idx_match = i - len(matcher) + 1
            print('found possible match starting at {}'.format(str(idx_match)))
            start_idx_match.append(idx_match)

    for i in start_idx_match:
        if input[i:i+len(matcher)] == matcher:
            print('verified match at {}'.format(i))

rabin_karpe_match('abcdefcdegdce', 'cd')