package com.Fooddelivery.Food_delivery.model;



    public class MenuItem {

        private String id;
        private String name;
        private String category;
        private String description;
        private double price;
        private boolean available;
        private String imageUrl;

        public MenuItem() {}

        public MenuItem(String id, String name, String category,
                        String description, double price,
                        boolean available, String imageUrl) {
            this.id          = id;
            this.name        = name;
            this.category    = category;
            this.description = description;
            this.price       = price;
            this.available   = available;
            this.imageUrl    = imageUrl;
        }

        public String getId()                      { return id; }
        public void   setId(String id)             { this.id = id; }

        public String getName()                    { return name; }
        public void   setName(String name)         { this.name = name; }

        public String getCategory()                { return category; }
        public void   setCategory(String category) { this.category = category; }

        public String getDescription()                   { return description; }
        public void   setDescription(String description) { this.description = description; }

        public double getPrice()                   { return price; }
        public void   setPrice(double price)       { this.price = price; }

        public boolean isAvailable()                   { return available; }
        public void    setAvailable(boolean available) { this.available = available; }

        public String getImageUrl()                    { return imageUrl; }
        public void   setImageUrl(String imageUrl)     { this.imageUrl = imageUrl; }

        // Converts MenuItem object to pipe delimited string for file storage

        public String toFileString() {
            return String.join("|",
                    id, name, category, description,
                    String.valueOf(price),
                    String.valueOf(available),
                    imageUrl == null ? "" : imageUrl);
        }

        public static MenuItem fromFileString(String line) {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 7) return null;
            return new MenuItem(
                    parts[0], parts[1], parts[2], parts[3],
                    Double.parseDouble(parts[4]),
                    Boolean.parseBoolean(parts[5]),
                    parts[6]);
        }

        @Override
        public String toString() {
            return "MenuItem{id='" + id + "', name='" + name +
                    "', category='" + category + "', price=" + price + "}";
        }
    }


