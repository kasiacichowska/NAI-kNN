"K-NN Classifier" to program, który umożliwia klasyfikację danych przy użyciu algorytmu k najbliższych sąsiadów (k-NN). 

Użytkownik może podać trzy główne argumenty:
  1. K:           Dodatnia liczba naturalna, która określa liczbę sąsiadów branych pod uwagę przy klasyfikacji.
  2. Train-set:   Nazwa pliku zawierającego zbiór treningowy w formacie CSV, na podstawie którego będzie dokonywana klasyfikacja.
  3. Test-set:    Nazwa pliku zawierającego zbiór testowy w formacie CSV, który zostanie sklasyfikowany przez program na podstawie danych treningowych.

Po przetworzeniu danych treningowych i testowych, program przeprowadza klasyfikację wszystkich obserwacji z pliku test-set na podstawie pliku train-set. 
Dodatkowo, program podaje dokładność (accuracy) tej klasyfikacji, informując użytkownika o tym, jak dobrze model radzi sobie z przewidywaniem klas.

Interfejs programu umożliwia również użytkownikowi wprowadzanie pojedynczych wektorów danych do klasyfikacji. Na podstawie danych treningowych, 
program przewiduje etykietę k-NN dla tych wektorów i zwraca odpowiedź użytkownikowi. Interfejs może być tekstowy lub graficzny, w zależności od preferencji użytkownika.
