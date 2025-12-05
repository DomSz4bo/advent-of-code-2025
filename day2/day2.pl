:- use_module(library(readutil)).
:- use_module(library(lists)).

decode(X, [Li, Hi]) :- 
    string(X), 
    split_string(X, "-", "", [L|[H|[]]]),
    number_string(Li, L),
    number_string(Hi, H), !.
decode(Xs, Ys) :- is_list(Xs), decode(Xs, [], Zs), reverse(Zs, Ys).

decode([], Acc, Acc).
decode([X|Xs], Acc, W) :- decode(X, Acc, Acc1), decode(Xs, Acc1, W), !.
decode(X, Acc, [W|Acc]) :- string(X), decode(X, W). 


invalid_id_v1(X) :- number_string(X, Y), string_concat(P, P, Y).

invalid_id_v2(X) :-
    number_string(X, W),
    string_length(W, L), 
    L2 is div(L, 2),
    between(1, L2, K),
    sub_string(W, 0, K, _, Part),
    partN(W, Part), !.

partN("", _) :- !.
partN(Xs, Part) :- 
    Part \= "",
    string_concat(Part, Rest, Xs),
    partN(Rest, Part).

sum_of_invalid_ids(File, Sum) :-
    read_file_to_string(File, Content, []),
    split_string(Content, ",", "\n", Xs),
    decode(Xs, Ranges),
    findall(Id, (
        member([L|[H|[]]], Ranges), 
        between(L, H, Id),
        invalid_id_v2(Id)
        ), InvalidIds),
    sum_list(InvalidIds, Sum).

