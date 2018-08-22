/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package palindrome;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WordTaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testNullInput() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", ""))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value(""));
    }


    @Test
    public void paramWordTaskShouldReturnSuppliedText() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", "flash"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("flash"));
    }

    @Test
    public void paramWordTaskTestNonAlphaCharacters() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", "!-2.#"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("!-2.#"))
        ;
    }

    @Test
    public void testReversal() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", "baby"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("baby"))
                .andExpect(jsonPath("$.reversed").value("ybab"));
    }

    @Test
    public void testPalindromeTrue() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", "racecar"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("racecar"))
                .andExpect(jsonPath("$.reversed").value("racecar"))
                .andExpect(jsonPath("$.palindrome").value("true"));
    }

    @Test
    public void testCaseInsensitivePalindromeTrue() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", "RaceCar"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.reversed").value("raCecaR"))
                .andExpect(jsonPath("$.palindrome").value("true"));
    }

    @Test
    public void testVeryLongPalindromeTrue() throws Exception {
        final String longPalindrome = "abbcccddddeeeeeffffffggggggghhhhhhhhiiiiiiiiijjjjjjjjjjkkkkkkkkkkkllllllllllllmmmmmmmmmmmmmnnnnnnnnnnnnnnoooooooooooooooppppppppppppppppqqqqqqqqqqqqqqqqqrrrrrrrrrrrrrrrrrrsssssssssssssssssssttttttttttttttttttttuuuuuuuuuuuuuuuuuuuuuvvvvvvvvvvvvvvvvvvvvvvwwwwwwwwwwwwwwwwwwwwwwwxxxxxxxxxxxxxxxxxxxxxxxxyyyyyyyyyyyyyyyyyyyyyyyyyzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzyyyyyyyyyyyyyyyyyyyyyyyyyxxxxxxxxxxxxxxxxxxxxxxxxwwwwwwwwwwwwwwwwwwwwwwwvvvvvvvvvvvvvvvvvvvvvvuuuuuuuuuuuuuuuuuuuuuttttttttttttttttttttsssssssssssssssssssrrrrrrrrrrrrrrrrrrqqqqqqqqqqqqqqqqqppppppppppppppppooooooooooooooonnnnnnnnnnnnnnmmmmmmmmmmmmmllllllllllllkkkkkkkkkkkjjjjjjjjjjiiiiiiiiihhhhhhhhgggggggffffffeeeeeddddcccbba";
        this.mockMvc.perform(get("/palindrome").param("word", longPalindrome))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.reversed").value(longPalindrome))
                .andExpect(jsonPath("$.palindrome").value("true"));
    }

    @Test
    public void testValidDictionaryWord() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", "Eureka"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.defined").value("true"));
    }

    @Test
    public void testInvalidDictionaryWord() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", "Fartnight"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.defined").value("false"));
    }


    @Test
    public void testInputHasSpaces() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", "Hello World"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("Hello"))
                .andExpect(jsonPath("$.reversed").value("olleH"));
    }


    @Test
    public void testInputWithSpaces() throws Exception {
        this.mockMvc.perform(get("/palindrome").param("word", "Able was I ere I saw Elba"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("Able"))
                .andExpect(jsonPath("$.reversed").value("elbA"))
                .andExpect(jsonPath("$.defined").value("true"))
        ;
    }


}
