import java.io.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.Scanner;

public class MusicOrganizer {

    private ArrayList<String> library;

    public MusicOrganizer() {

        library = new ArrayList<>();
        try (FileReader file = new FileReader("musiclibrary.txt"); BufferedReader reader = new BufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                library.add(line);
            }
            for(int i = 0; i < library.size(); i++){
                if(library.get(i).equals("")){
                    library.remove(library.get(i));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void printLibrary() {
        int counter =0;
        for (int i = 0; i < library.size(); i++) {
            System.out.println(library.get(i));
            counter++;
            if(counter == 7){
                counter = 0;
                System.out.println();
            }
        }
    }

    public void printToDiffConsole(String name) throws IOException {
        try (FileWriter file = new FileWriter(name + ".txt"); BufferedWriter writer = new BufferedWriter(file)) {
            int counter = 0;
            for (int i = 0; i < library.size(); i++) {
                writer.write(library.get(i));
                writer.newLine();
                counter++;
                if(counter == 7){
                    counter = 0;
                    writer.newLine();
                }
            }
        }
    }

    public void addAlbum(){
        Scanner user = new Scanner(System.in);

        System.out.println("What is the name of the artist");
        String artist = user.nextLine();

        library.add("Artist: " + artist);

        System.out.println("What is the album title?");
        String album = user.nextLine();

        library.add("Album Title: " + album);

        System.out.println("What studio/label do they work under?");
        String label = user.nextLine();

        library.add("Studio/Label: " + label);

        System.out.println("What was their peak position on the billboard charts?");
        String peakPos = user.nextLine();

        library.add("Peak Position on Billboards: " + peakPos);

        System.out.println("What was the date they peaked? (Give month, day, and year)");
        String peakDate = user.nextLine();

        library.add("Peak Date: " + peakDate);

        System.out.println("How many weeks was it on the chart?");
        String numWeeks = user.nextLine();

        library.add("Weeks on chart: " + numWeeks);

        System.out.println("Give an one line review of the album");
        String review = user.nextLine();
        library.add("One line review: \"" + review+ "\"");
    }

    public String findArtist(String name, String specificAlbum, String console) throws IOException {
        int numAlbums = 0;
        for(int i = 0; i < library.size(); i +=7){
            if(library.get(i).toLowerCase().contains(name.toLowerCase())){
                numAlbums++;
            }
        }

        if(numAlbums != 0){

            if(specificAlbum == null) {
                ArrayList<Integer> albumNums = new ArrayList<>();
                for (int p = 0; p < library.size(); p += 7) {
                    if (library.get(p).toLowerCase().contains(name.toLowerCase())) {
                        albumNums.add(p);
                    }
                }

//                System.out.println("Do you want to album information in console(press 1) or in different file(press 2)");
//                int console = user.nextInt();
//                user.nextLine();

                if(console == null){
                    for (int t = 0; t < albumNums.size(); t++) {
                        int l = albumNums.get(t);
                        int temp = l;
                        while (l < temp + 7) {
                            System.out.println(library.get(l));
                            l++;
                        }
                        System.out.println();
                    }
                    return "Here you go";
                } else {

                    try(FileWriter file = new FileWriter(console +".txt"); BufferedWriter writer = new BufferedWriter(file)){
                        for (int t = 0; t < albumNums.size(); t++) {
                            int l = albumNums.get(t);
                            int temp = l;
                            while (l < temp + 7) {
                                writer.write(library.get(l));
                                writer.newLine();
                                l++;
                            }
                            writer.newLine();

                        }
                        return "All set";
                    }

                }


            } else { //user pressed 1
                int b = 0;
                while(b < library.size() && !(library.get(b).toLowerCase().contains(specificAlbum.toLowerCase()))){
                   b++;
                }

                if(b == library.size()){
                    return "No album found under that artist";
                } else {

//                    System.out.println("Do you want to album information in console(press 1) or in different file(press 2)");
//                    int console = user.nextInt();
//                    user.nextLine();

                    if(console == null){
                        int temper = b -1;
                        for(int d = b-1; d < temper + 7; d++){
                            System.out.println(library.get(d));
                        }
                        return "Here you go";
                    } else {
//                        System.out.println("What do you want the name of the different file to be?");
//                        String fileName = user.nextLine();

                        try(FileWriter file = new FileWriter(console +".txt"); BufferedWriter writer = new BufferedWriter(file)){
                            int tempy = b-1;
                            for(int d = b-1; d < tempy + 7; d++){
                                writer.write(library.get(d));
                                writer.newLine();
                            }
                            writer.newLine();
                            return "All set";
                        }

                    }


                }

            }
        }
        return "No artist with that name in library";
    }

    public String updateReview() {
        Scanner user = new Scanner(System.in);
        System.out.println("What artist do you want to review");
        String name = user.nextLine();

        System.out.println("What album do you want to review");
        String album = user.nextLine();
        for(int p = 0; p < library.size(); p++){
            if(library.get(p).toLowerCase().contains(name.toLowerCase())){
                break;
            }
            if(p == library.size()-1){
                return "Artist not in library";
            }
        }

        for(int k = 0; k < library.size(); k++){
            if(library.get(k).toLowerCase().contains(album.toLowerCase())){
                break;
            }
            if(k == library.size()-1){
                return "Album not in library";
            }
        }

        int i = 0;
        while( (!library.get(i+1).toLowerCase().contains(album.toLowerCase()))){
            i++;
        }
        i+=6;
        int start = library.get(i).indexOf(":") + 2;

        System.out.println("To what do you want to change the review to?");
        String review = user.nextLine();

        library.set(i, library.get(i).substring(0,start+1) + review + "\"");
        System.out.println(i);
        System.out.println(library.get(i));

        return "Review updated";
    }

    public void menu() throws IOException {
        Scanner user = new Scanner(System.in);
        System.out.println("Here are your options");
        System.out.println(" 0: Quit\n" +
                " 1: Print all to Console\n" +
                " 2: Print all to Different File\n" +
                " 3: Add new album\n" +
                " 4: Find specific artist\n" +
                " 5: Update a review for a particular album\n" +
                " 6: Show menu options");
        System.out.println("Press an option");
        int response = user.nextInt();
        user.nextLine();
        while(response != 0){
            if(response == 1){
                printLibrary();
            } else if(response == 2){
                System.out.println("What do you want to make the different file?");
                String name = user.nextLine();
                printToDiffConsole(name);
            } else if(response ==3){
                addAlbum();
            } else if(response ==4){
                System.out.println("What artist do you want to find");
                Scanner ui = new Scanner(System.in);
                String name = ui.nextLine();
                System.out.println("Would you like to receive specific album(press 1)  or all albums(press 2)");
                int num = user.nextInt();
                user.nextLine();
                String albumName;
                if(num == 1){
                    System.out.println("Which album are you looking for?");
                     albumName = user.nextLine();
                } else{
                    albumName = null;
                }
                String print;
                System.out.println("Do you want to album information in console(press 1) or in different file(press 2)");
                int console = user.nextInt();
                user.nextLine();
                if(console ==1){
                    print = null;
                } else{
                    System.out.println("What do you want the name of the different file to be?");
                    print = user.nextLine();
                }
                System.out.println(findArtist(name, albumName, print));
            } else if(response == 5){
                System.out.println(updateReview());
            } else if(response == 6){
                System.out.println("Here are you options");
                System.out.println(" 0: Quit\n" +
                        " 1: Print all to Console\n" +
                        " 2: Print all to Different File\n" +
                        " 3: Add new album\n" +
                        " 4: Find specific artist\n" +
                        " 5: Update a review for a particular album\n" +
                        " 6: Show menu options");
            }
            System.out.println("Press another option");
            response = user.nextInt();
            user.nextLine();
        }
        System.out.println("bye bye");
    }


}


