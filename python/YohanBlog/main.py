import os
import csv
from post import Post

file_path = "./myvenv/Chapter12/data.csv"

# lists of post
post_list = []

# if there is data.csv 
if os.path.exists(file_path):
    # load posts
    print("Loading posts...")
    f = open(file_path, "r", encoding="utf8)")
    reader = csv.reader(f)
    for data in reader:
        # creating post instance
        post = Post(int(data[0]), data[1], data[2], int(data[3]))
        post_list.append(post)
else:
    # creating file
    f = open(file_path, "w", encoding="utf8", newline="")
    f.close()

#Writng post
def write_post():
    print("\n\n- Writing post -")
    title = input("The title of your post: ")
    content = input("Write the content")
    if post_list:
        id = post_list[-1].get_id()+1
    else:
        id = 1
    post = Post(id, title, content, 0)
    post_list.append(post)
    print("#Your post has been submitted")

#list of posts
def list_post():
    print("\n\n - Post lists")
    id_list = []
    for post in post_list:
        print("number: ", post.get_id())
        print("Title: ", post.get_title())
        print("Views: ", post.get_view_count())
        print("")
        id_list.append(post.get_id())

    while True:
        print("Select any number of posts. \"-1\" for back to menu")
        try: 
            id = int(input(">>>"))
            if id in id_list:
                detail_post(id)
                break
            elif id == -1:
                break
            else:
                print("Invalid input.")
        except ValueError:
            print("Input a number")

#post detail
def detail_post(id):
    print("\n\n- Post detail -")

    for post in post_list:
        if post.get_id() == id:
            #view + 1
            post.add_view_count()
            print("Number: ", post.get_id())
            print("Title: ", post.get_title())
            print("Content: ", post.get_content())
            print("Views: ", post.get_view_count())
            target_post = post

    while True:
        print("Select\n1.Edit\n2.Delete\n-1. Back to menu")
        try:
            choice = int(input(">>>"))
            if choice == 1:
                update_post(target_post)
                break
            elif choice == 2:
                delete_post(target_post)
            elif choice == -1:
                break
            else:
                print("Invalid input")
        except ValueError:
            print("Input a number")

#Edit post
def update_post(target_post):
    print("\n\n- Edit post -")
    title = input("Input post title\n>>>")
    content = input("Input post content\n>>>")
    target_post.set_post(target_post.id, title, content, target_post.view_count)
    print("#The post has beem edited")

#Delete post
def delete_post(target_post):
    post_list.remove(target_post)
    print("#The post has beem deleted")

#Save the post
def save():
    f = open(file_path, "w", encoding="utf8", newline="")
    writer = csv.writer(f)
    for post in post_list:
        row = [post.get_id(), post.get_title(), post.get_content(), post.get_view_count()]
        writer.writerow(row)
    f.close()
    print("# Has been saved")
    print("# Program Exit")


# menu display
while True:
    print("\n\n- Yohan's Blog -")
    print("- Select menu -")
    print("1. Write a post")
    print("2. Lists of posts")
    print("3. Exit")
    try:
       choice = int(input(">>>"))
    except ValueError:
        print("Input a number from the list")
    else:
        if choice == 1:
            write_post()
        elif choice == 2:
            list_post()
        elif choice == 3:
            save()
            break



